package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private socio idsocio;
    
    
    private LocalDateTime fechacompra;
    private boolean active;
    private int cantidadAcciones;
    private String nombre_indice;
    private double precio;



}
