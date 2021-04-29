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
    // Produce una lista de arrays de object (Object), en las que el primer elemento es el símbolo y el segundo es sum(quantity)
	@NamedQuery(name="Position.byMembership",
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

    private boolean isActive; // si la posición está activa o no
    private int quantity; // cantidad de acciones

    @NotNull
    @ManyToOne
    private Symbol symbol;

}
