package es.ucm.fdi.stonks.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.yahoofinance.YahooFinance;
import com.yahoofinance.Stock;

public class Mercado {

    private List<String> simbolo; 

    /**
     * Devuelve el valor del simbolo en el momento actual
     * @param simbolo
     * @return
     */
    double getValor(String simbolo) {
        return 0;
    }

    void refresh(List<String> simbolos) {
        String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
        Map<String, Stock> stocks = YahooFinance.get(symbols); // single request
        Stock intel = stocks.get("INTC");
        Stock airbus = stocks.get("AIR.PA");
    }
}
