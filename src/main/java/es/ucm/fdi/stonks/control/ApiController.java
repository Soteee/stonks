package es.ucm.fdi.stonks.control;


import java.util.HashMap;
import java.util.Map;
import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
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
    public void actionBuy(@RequestParam long id, 
                        @RequestParam String stockName,
                        @RequestParam String amount,
                        Model model,
                        HttpServletResponse response) throws Exception{

        Membership member =   entityManager.find(Membership.class, id);     
        Position testPosition = new Position();
        testPosition.setMember(member);
        testPosition.setPrice(Float.parseFloat(getSymbol(stockName)));
        testPosition.setIndexName(stockName);
        testPosition.setPurchaseDate(java.time.LocalDateTime.now());
        testPosition.setQuantity(Integer.parseInt(amount));
        testPosition.setActive(true);
        try{
            entityManager.persist(testPosition);
        }catch(Exception e){
            System.out.println("bro eres bobo o que");
        }

        response.sendRedirect("/r/" + member.getRoom().getId());
    }

    public String getSymbol(String name){
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put(headerkey, headerkeyp2);
            headers.put(authname1, authname2);
            headers.put("useQueryString", "true");
           
            System.out.println(name);
            //System.out.println(url + name + "/financial-data");
            JSONObject json = Unirest.get(url + name + "/financial-data").headers(headers).asJson().getBody()
                    .getObject();
            //System.out.println(json.toString());
            if (json != null) {
                String valor = (json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt"));
                
                return valor;

            } else System.out.println("es nulo manin");
               

        } catch (UnirestException exception) {
            System.out.println("error bro");
        }
        return "1";
    }


}
