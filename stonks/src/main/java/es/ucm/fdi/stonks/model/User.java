package es.ucm.fdi.stonks.model;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @OneToOne(mappedBy = "owner_id")
    private long id;

    @OneToMany(targetEntity = Member.class)
    //salas de las que soy socio
    private List<Member> memberList;

}
