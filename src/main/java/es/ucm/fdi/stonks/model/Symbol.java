package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@NamedQueries({
    @NamedQuery(name="Symbol.all",
                query = "SELECT s FROM Symbol s"),
    @NamedQuery(name="Symbol.byName",
                query = "SELECT s FROM Symbol s WHERE s.name = :name")
})
public class Symbol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private double value;
   
    private LocalDateTime updatedOn;
}
