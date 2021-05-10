package es.ucm.fdi.stonks.control;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

import es.ucm.fdi.stonks.model.Membership;
import es.ucm.fdi.stonks.model.Position;
import es.ucm.fdi.stonks.model.Room;
import es.ucm.fdi.stonks.model.Symbol;
import es.ucm.fdi.stonks.model.Position.Side;

public class StaticMethods {
    private static String url = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/qu/quote/";
    private static String headerkey = "x-rapidapi-key";
    private static String headerkeyp2 = "1a2c0118edmsh2fe8520e5114c90p147e61jsn9a2bc42af118";
    private static String authname1 = "x-rapidapi-host";
    private static String authname2 = "yahoo-finance15.p.rapidapi.com";
    
    private static double getSymbol(String name) throws Exception {
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
        
        return Double.parseDouble(json.getJSONObject("financialData").getJSONObject("currentPrice").getString("fmt").replaceAll(",", ""));
    }

    public static void refreshStockValues(EntityManager em) {
        List<Symbol> symbolList = em
								.createNamedQuery("Symbol.lasts")
								.getResultList();

        // Use the same value for evert stock so we can extract them all at once easily from DB
        LocalDateTime now = LocalDateTime.now();

		for (Symbol o : symbolList) {
			// If more than 24 hours have passed
			if(o.getUpdatedOn() == null ||
				Duration.between(o.getUpdatedOn(), now).compareTo(Duration.ofHours(24)) > 0){
                
                try {
                    Symbol newSymbol = new Symbol();

                    newSymbol.setName(o.getName());
                    newSymbol.setUpdatedOn(now);
                    newSymbol.setValue(getSymbol(o.getName()));
                    

                    // Insert new symbols in their respective rooms and those rooms in their respective symbols
                    List<Room> roomsWithNewSymbol = new ArrayList<>();
                    for (Room r : o.getRooms()){
                        r.getSymbols().add(newSymbol);
                        em.persist(r);

                        roomsWithNewSymbol.add(r);
                    }
                    newSymbol.setRooms(roomsWithNewSymbol);

                    em.persist(newSymbol);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		}
    }

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
