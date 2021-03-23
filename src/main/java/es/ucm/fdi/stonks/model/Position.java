package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Member member;

    @NotNull
    private LocalDateTime purchaseDate; // fecha de compra

    private boolean isActive; // si la posición está activa o no
    private int quantity; // cantidad de acciones

    @NotNull
    @Size(max=20)
    private String indexName; // nombre de la posición; no puede repetirse

    private double price; // precio medio ponderado de la posición

}
