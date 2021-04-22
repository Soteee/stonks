package es.ucm.fdi.stonks.control;


import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.stonks.model.Market;
import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
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

@Data
@Controller("action_buy")
public class ApiController {
    @Autowired
    private EntityManager entityManager;
    private String url = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/qu/quote/";
    private String headerkey = "x-rapidapi-key";
    private String headerkeyp2 = "1a2c0118edmsh2fe8520e5114c90p147e61jsn9a2bc42af118";
    private String authname1 = "x-rapidapi-host";
    private String authname2 = "yahoo-finance15.p.rapidapi.com";
    
    @PostMapping("/action_buy")
    @Transactional
    public void actionBuy(HttpSession session,
                        @RequestParam String stockName,
                        @RequestParam String amount,
                        @RequestParam long room_id,
                        Model model,
                        HttpServletResponse response) throws Exception{

        
        

        Room r = entityManager.find(Room.class, room_id);
        Market m = (Market) r.getMarket();
        if(!m.getListElement(stockName)){
            System.out.print("funcionando depta madre");
            m.getStocks().add(stockName);
        }
        
        String stockValue = getSymbol(stockName);
        
        if (stockValue.equals("error")){
            response.sendError(400);
        }
        else{
            Membership member = (Membership) entityManager.createNamedQuery("Membership.byUserAndRoom")
                                            .setParameter("user", session.getAttribute("u"))
                                            .setParameter("room", entityManager.find(Room.class, room_id))
                                            .getSingleResult();
            Position testPosition = new Position();
            testPosition.setMember(member);
            testPosition.setPrice(Float.parseFloat(stockValue));
            testPosition.setIndexName(stockName);
            testPosition.setPurchaseDate(java.time.LocalDateTime.now());
            testPosition.setQuantity(Integer.parseInt(amount));
            testPosition.setActive(true);
            entityManager.persist(testPosition);

            response.sendRedirect("/r/" + room_id);
        }
    }

    public String getSymbol(String name) throws Exception {
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
            return "error";
        }
        else{

            
        }
        return json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt");
    }
}
