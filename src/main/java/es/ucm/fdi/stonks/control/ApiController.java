package es.ucm.fdi.stonks.control;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import lombok.Data;

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
    
    @PostMapping("/action_buy")
    @Transactional
    public void actionBuy(HttpSession session,
                        @RequestParam String stockName,
                        @RequestParam String amount,
                        @RequestParam long room_id,
                        Model model,
                        HttpServletResponse response) throws Exception{    
        
        Membership member = (Membership) entityManager.createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", session.getAttribute("u"))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();

        Position newPosition = new Position();
        newPosition.setMember(member);

        Symbol symbol = (Symbol) entityManager
                                .createNamedQuery("Symbol.byName")
                                .setParameter("name", stockName)
                                .getSingleResult();

        newPosition.setSymbol(symbol);
        newPosition.setPurchaseDate(java.time.LocalDateTime.now());
        newPosition.setQuantity(Integer.parseInt(amount));
        newPosition.setActive(true);
        entityManager.persist(newPosition);

        response.sendRedirect("/r/" + room_id);
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

    public static float getSymbol(String name) throws Exception {
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
        
        return Float.parseFloat(json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt"));
    }
}
