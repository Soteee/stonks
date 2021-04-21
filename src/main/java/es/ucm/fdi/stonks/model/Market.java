package es.ucm.fdi.stonks.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

//import com.yahoofinance.YahooFinance;
//import com.yahoofinance.Stock;


/**
 *  @author Javier
 *  La clase Market se encarga de cargar una lista de stocks en una sala.
 *  Existen distintos tipos de mercado, que tendrán listas distintas, cada sala tiene asignado un mercado
 *  Probablmente haga falta otra API para otros mercados pues YahooFinance solo trabaja con Nasdaq, por el momento, solo habrá Nasdaq
 *  Cuando se selecciona un mercado para una sala, se comprueba que el stock que se pretende comprar en esa sala esta en la lista, si está
 *  se permite la compra, si no, se comprueba que sea de ese mercado, en caso de estar, se compra y se añade a la lista.
 *  TODO: No seria mala trabajar mercados como enumerados o listas fijas.
 *  TODO: añadir api de cryptos.
 */
@Entity
@Data
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull
    private String name;
    
    @OneToMany
    private List<Room> usedByRooms;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> stocks; 


}
