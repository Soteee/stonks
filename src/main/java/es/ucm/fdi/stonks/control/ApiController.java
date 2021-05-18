package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.User;
import es.ucm.fdi.stonks.model.Position.Side;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
public class ApiController {
    @Autowired
    private EntityManager entityManager;
    
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
                                        .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
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
                                        .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();
        Symbol symbol = entityManager.find(Symbol.class, symbol_id);

        int quantityToSell = Integer.parseInt(quantity);
        int userQuantity =  StaticMethods.computeQuantity(entityManager, member, symbol);

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
}
