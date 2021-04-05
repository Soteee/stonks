package es.ucm.fdi.stonks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Una sala, en la cual un grupo de usuarios apuesta a la bolsa. 
 * Cada sala está restringida a sus usuarios y a un mercado concreto.
 */

@Entity
@Data
public class Room {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @NotNull
   @Size(max=20)
   private String name; // nombre de la sala

   private boolean isPublic; // visibilidad de la sala: pública/privada
   private int weeklyCash; // dinero que se da todas las semanas; si es 0 no tiene esta función

   private int maxUsers; // número máximo de usuarios que admite la sala
   private int startBalance; // dinero con el que se empieza en la sala
   private int cash2Win; // dinero con el que se gana la partida en la sala; si es 0 no tiene esta función

   @NotNull
   private LocalDateTime creationDate; // fecha de creación

   private LocalDate expirationDate; // fecha de caducidad; si es NULL no tiene esta función

   @ManyToOne
   private User admin; // administrador de la sala

   @OneToMany
   @JoinColumn(name = "room_id")
   private List<Membership> memberList; // lista de relaciones usuario-sala

}
