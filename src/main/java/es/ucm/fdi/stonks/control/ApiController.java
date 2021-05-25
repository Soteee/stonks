package es.ucm.fdi.stonks.control;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.User;
import es.ucm.fdi.stonks.model.Position.Side;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
@RequestMapping("api")
public class ApiController {
    @Autowired
    private EntityManager entityManager;
    
    @GetMapping(path = "/userInfo", produces = "application/json")
    @ResponseBody
    public String getUserStocks(HttpSession session,
                                @RequestParam(value = "r") long room_id,
                                HttpServletResponse response) throws Exception{
        
        Room room = entityManager.find(Room.class, room_id);
        
        Membership membership = (Membership) entityManager
                                        .createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
                                        .setParameter("room", room)
                                        .getSingleResult();

        if (membership == null){
            response.sendError(400);
        }

        List<?> lastSymbols = entityManager
                                .createNamedQuery("Symbol.lastsByRoom")
                                .setParameter("room", room)
                                .getResultList();

        JSONObject json = new JSONObject();

        json.put("balance", String.format("%.02f",membership.getBalance()));

        JSONArray stocks = new JSONArray();
        for (Symbol symbol : (List<Symbol>) lastSymbols) {
            int quantity = StaticMethods.computeQuantity(entityManager, membership, symbol);
            if (quantity != 0){
                JSONObject stock = new JSONObject();
                stock.put(symbol.getName(), quantity);
                stocks.put(stock);
            }
        }
        json.put("stocks", stocks);

        return json.toString();
    }

    @GetMapping(path = "/usersInRoom", produces = "application/json")
    @ResponseBody
    public String getUsersInRoom(HttpSession session,
                                @RequestParam(value = "r") long room_id,
                                HttpServletResponse response) throws Exception{
        
        Room room = entityManager.find(Room.class, room_id);

        List<User> users = entityManager
                            .createNamedQuery("User.inRoom")
                            .setParameter("room", room)
                            .getResultList();

        JSONArray json = new JSONArray();
        for (User user : users) {
            Membership m = (Membership) entityManager
                            .createNamedQuery("Membership.byUserAndRoom")
                            .setParameter("room", room)
                            .setParameter("user", user)
                            .getSingleResult();

            JSONObject user_json = new JSONObject();
            user_json.put("balance", String.format("%.02f",m.getBalance()));
            user_json.put("id", user.getId());
            user_json.put("username", user.getUsername());
            json.put(user_json);
        }

        return json.toString();
    }

    @GetMapping("isFollowing")
    @ResponseBody
    @Transactional
    public String isFollowing(HttpSession session,
                            @RequestParam(value = "u") long user_id,
                            HttpServletResponse response){
        User follower = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
        User followed = entityManager.find(User.class, user_id);
            
        if (follower.getFollowing().contains(followed)){
            return "{\"result\": true }";
        }
        else{
            return "{\"result\": false }";
        }
    }

    @PostMapping("/follow")
    @ResponseBody
    @Transactional
    public String follow(HttpSession session,
                            @RequestBody JsonNode o,
                            HttpServletResponse response) throws Exception{

        User follower = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
        User followed = entityManager.find(User.class, o.get("user").asLong());

        if (follower.getFollowing().contains(followed)){
            response.sendError(400);
        }

        follower.getFollowing().add(followed);

        entityManager.persist(follower);

        return "{\"result\":\"success\"}";
    }
    
    @PostMapping("/unfollow")
    @ResponseBody
    @Transactional
    public String unfollow(HttpSession session, 
                            @RequestBody JsonNode o, 
                            HttpServletResponse response) throws Exception {

        User follower = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());
        User followed = entityManager.find(User.class, o.get("user").asLong());

        if (!follower.getFollowing().contains(followed)) { 
            response.sendError(400);
        }
        
        follower.getFollowing().remove(followed);
        entityManager.persist(follower);

        return "{\"result\":\"success\"}";
    }

    @PostMapping("/buy")
    @ResponseBody
    @Transactional
    public String buy(HttpSession session,
                        @RequestBody JsonNode o,
                        HttpServletResponse response) throws Exception{    
        
        long symbol_id = o.get("symbol_id").asLong();
        int quantity = o.get("quantity").asInt();
        long room_id = o.get("room_id").asLong();

        Membership member = (Membership) entityManager
                                        .createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();

        Symbol symbol = entityManager.find(Symbol.class, symbol_id);

        double price = symbol.getValue() * quantity;

        if (member == null){
            response.sendError(400);
        }

        if (member.getBalance() < price){
            return "{\"result\": \"No tienes suficiente dinero\"}";
        }

        Position newPosition = new Position();
        newPosition.setMember(member);
        newPosition.setSymbol(symbol);
        newPosition.setPurchaseDate(java.time.LocalDateTime.now());
        newPosition.setQuantity(quantity);
        newPosition.setSide(Side.BUY);
        newPosition.setValue(price);
        entityManager.persist(newPosition);

        member.setBalance(member.getBalance() - newPosition.getValue());
        entityManager.persist(member);

        return "{\"result\": \"success\"}";
    }

    @PostMapping("/sell")
    @ResponseBody
    @Transactional
    public String sell(HttpSession session,
                        @RequestBody JsonNode o,
                        HttpServletResponse response) throws Exception{

        long symbol_id = o.get("symbol_id").asLong();
        int quantity = o.get("quantity").asInt();
        long room_id = o.get("room_id").asLong();

        Membership member = (Membership) entityManager
                                        .createNamedQuery("Membership.byUserAndRoom")
                                        .setParameter("user", entityManager.find(User.class,((User)session.getAttribute("u")).getId()))
                                        .setParameter("room", entityManager.find(Room.class, room_id))
                                        .getSingleResult();
        Symbol symbol = entityManager.find(Symbol.class, symbol_id);

        int userQuantity =  StaticMethods.computeQuantity(entityManager, member, symbol);

        if (member == null){
            response.sendError(400);
        }

        if (quantity > userQuantity){
            return "{\"result\": \"No tienes suficientes acciones\"}";
        }

        Position newPosition = new Position();
        newPosition.setMember(member);
        newPosition.setSymbol(symbol);
        newPosition.setPurchaseDate(java.time.LocalDateTime.now());
        newPosition.setQuantity(quantity);
        newPosition.setSide(Side.SELL);
        newPosition.setValue(symbol.getValue() * quantity);
        entityManager.persist(newPosition);

        member.setBalance(member.getBalance() + newPosition.getValue());
        entityManager.persist(member);

        return "{\"result\": \"success\"}";
    }
}
