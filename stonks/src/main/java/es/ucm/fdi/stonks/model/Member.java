package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Room.class)
    private Room room;

//    private LocalDateTime joinDate;
//    private double balance;

    @OneToMany
    private List<Position> positionList = new ArrayList<>();
    
}
