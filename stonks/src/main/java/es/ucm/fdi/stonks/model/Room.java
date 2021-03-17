package es.ucm.fdi.stonks.model;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Room {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String name;

   @OneToMany(targetEntity = Member.class)
   private List<Member> memberList;

}
