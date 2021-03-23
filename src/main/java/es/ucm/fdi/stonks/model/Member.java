package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @NotNull
    private LocalDateTime joinDate; // fecha en la que el usuario se unió a la sala

    private double balance; // dinero que tiene el usuario en la sala

    @OneToMany
    @JoinColumn (name = "position_id")
    private List<Position> positionList = new ArrayList<>(); // lista de posiciones
    
}
