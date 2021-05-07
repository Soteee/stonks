package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Una apuesta de N acciones - hecha por un usuario de una sala concreta
 */
@Entity
@Data
@NamedQueries({
    @NamedQuery(name="Position.byMembershipAndSymbol",
                query="SELECT p FROM Position p "
                    + "WHERE p.member = :membership AND p.symbol = :symbol"),

    // Produce una lista de arrays de object (Object), en las que el primer elemento es el símbolo y el segundo es sum(quantity)
	@NamedQuery(name="Position.quantityByMembership",
	            query="SELECT p.symbol, sum(p.quantity) FROM Position p "
                    + "WHERE p.member = :membership "
                    + "GROUP BY p.symbol")
})
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Membership member;

    @NotNull
    private LocalDateTime purchaseDate; // fecha de compra

    private int quantity; // cantidad de acciones

    // TODO: Java has Currency class that represents the ISO 4217 currency codes. BigDecimal is the best type for representing currency decimal values.
    private double value; // precio de la acción en el momento de la compra/venta

    // Representa el tipo de movimiento de una posición, si es una compra o una venta.
    public enum Side {
		BUY,
		SELL
	}
    private Side side;  // Si la posición corresponde a una compra o una venta
    
    @NotNull
    @ManyToOne
    private Symbol symbol;

}
