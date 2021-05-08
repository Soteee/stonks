package es.ucm.fdi.stonks.control;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.Position.Side;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.Unirest;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.json.JSONObject;

@Controller
public class ApiController {
    @Autowired
    private EntityManager entityManager;

    private static String url = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/qu/quote/";
    private static String headerkey = "x-rapidapi-key";
    private static String headerkeyp2 = "1a2c0118edmsh2fe8520e5114c90p147e61jsn9a2bc42af118";
    private static String authname1 = "x-rapidapi-host";
    private static String authname2 = "yahoo-finance15.p.rapidapi.com";
    
    @PostMapping("/buy")
    @Transactional
    public void buy(HttpSession session,
                        @RequestParam long symbol_id,
                        @RequestParam String quantity,
                        @RequestParam long room_id,
                        Model model,
                        HttpServletResponse response) throws Exception{    
        
        Membership member = (Membership) entityManager
                                        .createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", session.getAttribute("u"))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();

        Symbol symbol = entityManager.find(Symbol.class, symbol_id);

        int quantityToBuy = Integer.parseInt(quantity);
        double price = symbol.getValue() * quantityToBuy;
    
        if (member.getBalance() >= price){
            Position newPosition = new Position();
            newPosition.setMember(member);
            newPosition.setSymbol(symbol);
            newPosition.setPurchaseDate(java.time.LocalDateTime.now());
            newPosition.setQuantity(quantityToBuy);
            newPosition.setSide(Side.BUY);
            newPosition.setValue(price);
            entityManager.persist(newPosition);

            member.setBalance(member.getBalance() - newPosition.getValue());
            entityManager.persist(member);
        
            response.sendRedirect("/r/" + room_id);
        }
        else{
            response.sendError(400);    // TODO: Enviar error informativo (no un 400 xd)
        }
    }

    @PostMapping("/sell")
    @Transactional
    public void sell(HttpSession session,
                        @RequestParam long symbol_id,
                        @RequestParam String quantity,
                        @RequestParam long room_id,
                        Model model,
                        HttpServletResponse response) throws Exception{

        Membership member = (Membership) entityManager
                                        .createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", session.getAttribute("u"))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();
        Symbol symbol = entityManager.find(Symbol.class, symbol_id);

        int quantityToSell = Integer.parseInt(quantity);
        int userQuantity = computeQuantity(entityManager, member, symbol);

        if (quantityToSell <= userQuantity){
            Position newPosition = new Position();
            newPosition.setMember(member);
            newPosition.setSymbol(symbol);
            newPosition.setPurchaseDate(java.time.LocalDateTime.now());
            newPosition.setQuantity(quantityToSell);
            newPosition.setSide(Side.SELL);
            newPosition.setValue(symbol.getValue() * quantityToSell);
            entityManager.persist(newPosition);

            member.setBalance(member.getBalance() + newPosition.getValue());
            entityManager.persist(member);

            response.sendRedirect("/r/" + room_id);
        }
        else{
            response.sendError(400);    // TODO: Enviar error informativo (no un 400 xd)
        }
    }

    // Sólo se puede llamar desde admin
    @PostMapping("/refresh")
    @Transactional
    public void refreshAll(HttpServletResponse response) throws Exception{

        List<Symbol> symbolList = entityManager
                                .createNamedQuery("Symbol.all")
                                .getResultList();

        for (Symbol o : symbolList) {
             o.setValue(getSymbol(o.getName()));
             o.setUpdatedOn(java.time.LocalDateTime.now());
             entityManager.persist(o);
        }

        response.sendRedirect("/admin/");
    }

    public static double getSymbol(String name) throws Exception {
        /*
        Map<String, String> headers = new HashMap<>();
        headers.put(headerkey, headerkeyp2);
        headers.put(authname1, authname2);
        headers.put("useQueryString", "true");

        JSONObject json = Unirest.get(url + name + "/financial-data")
                                .headers(headers)
                                .asJson()
                                .getBody()
                                .getObject();

        if (json == null) {
            return -1.f;
        }
        
        return Double.parseDouble(json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt"));
        */

        return 500.0;
    }

    // TODO: Dónde ponemos esta función para que la pueda usar quien quiera
    // Calcula la cantidad de acciones de un símbolo que tiene un usuario en una sala
    public static int computeQuantity(EntityManager em, Membership m, Symbol s){
        int quantity = 0;
        List<Position> positions = em
                            .createNamedQuery("Position.byMembershipAndSymbol")
                            .setParameter("symbol", s)
                            .setParameter("membership", m)
                            .getResultList();

        for (Position position : positions) {
            if (position.getSide() == Side.BUY)
                quantity += position.getQuantity();
            else
                quantity -= position.getQuantity();
        }
    
        return quantity;
    }
}
