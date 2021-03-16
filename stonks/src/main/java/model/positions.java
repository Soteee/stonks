package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne(mappedBy = "owner_id")
    private long id;

    @ManyToOne
    private long idsocio;
    
    
    private LocalDateTime fechacompra;
    private boolean active;
    private int cantidadAcciones;
    private String nombre_indice;
    private double precio;



}
