package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Member member;
    
    private LocalDateTime purchaseDate;
    private boolean isActive;
    private int quantity;
    private String indexName;
    private double price;



}
