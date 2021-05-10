package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@NamedQueries({
    @NamedQuery(name="Symbol.all",
                query="SELECT s FROM Symbol s "
                    + "ORDER BY s.updatedOn DESC"),
    @NamedQuery(name="Symbol.lasts",
                query="SELECT s FROM Symbol s "
                    + "WHERE s.updatedOn = ("
                    +   "SELECT MAX(updatedOn) "
                    +   "FROM Symbol"
                    + ")"),
    @NamedQuery(name="Symbol.byName",
                query="SELECT s FROM Symbol s "
                    + "WHERE s.name = :name "
                    + "AND s.updatedOn = ("
                    +   "SELECT MAX(updatedOn) "
                    +   "FROM Symbol"
                    + ")"),
    @NamedQuery(name="Symbol.byRoom",
                query="SELECT s FROM Symbol s "
                    + "JOIN s.rooms r "
                    + "WHERE s.updatedOn = ("
                    +   "SELECT MAX(updatedOn) "
                    +   "FROM Symbol"
                    + ") "
                    + "AND r = :room")
})
public class Symbol {
    // Number of different symbols
    public static final int NUM_SYMBOLS = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private double value;
   
    private LocalDateTime updatedOn;

    @ManyToMany
    private List<Room> rooms; // Salas que utilizan este símbolo
}
