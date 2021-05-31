package es.ucm.fdi.stonks.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@NamedQueries({
	@NamedQuery(name="Membership.byUserAndRoom",
	        query="SELECT m FROM Membership m "
                + "WHERE m.room = :room AND m.user = :user"),
    @NamedQuery(name="Membership.getAllBalances",
            query="SELECT sum(m.balance) FROM Membership m")
})
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    /** fecha en la que el usuario se unió a la sala */
    @NotNull
    private LocalDateTime joinDate; 

    /** dinero que tiene el usuario en la sala */
    private double balance;

    @OneToMany
    @JoinColumn (name = "member_id")
    private List<Position> positionList = new ArrayList<>(); // lista de posiciones 
}

