package es.ucm.fdi.stonks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.checkerframework.checker.units.qual.m;

import lombok.Getter;
import lombok.Data;

/**
 * Una sala, en la cual un grupo de usuarios apuesta a la bolsa. 
 * Cada sala está restringida a sus usuarios y a un mercado concreto.
 */

@Entity
@Data
@NamedQueries({
   @NamedQuery(name="Room.all",
				query = "SELECT r FROM Room r"),
   @NamedQuery(name="Room.top",
            query="SELECT r  FROM Membership m "
               + "INNER JOIN Room r ON m.room = r.id "
               + "GROUP BY m.room "
               + "ORDER BY sum(m.balance) DESC"),
   @NamedQuery(name="Room.byUser",
            query="SELECT r FROM Membership m "
               + "JOIN Room r ON m.room = r.id "
               + "WHERE m.user = :user"),
   @NamedQuery(name="Room.bySearch",
            query="SELECT r FROM Room r WHERE r.name LIKE :name OR r.name LIKE '%:name%'")

})
public class Room {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @NotNull
   @Size(max=20)
   private String name; // nombre de la sala

   private boolean finished; // Si la sala ha acabado o no
   private boolean isPublic; // visibilidad de la sala: pública/privada
   private int weeklyCash; // dinero que se da todas las semanas; si es 0 no tiene esta función

   private int maxUsers; // número máximo de usuarios que admite la sala
   private int startBalance; // dinero con el que se empieza en la sala
   private int cash2Win; // dinero con el que se gana la partida en la sala; si es 0 no tiene esta función

   @OneToMany
	@JoinColumn(name = "room_id")	
	private List<Message> received = new ArrayList<>();	

   @NotNull
   private LocalDateTime creationDate; // fecha de creación

   private LocalDate expirationDate; // fecha de caducidad; si es NULL no tiene esta función

   @ManyToOne
   private User admin; // administrador de la sala

   @OneToMany
   @JoinColumn(name = "room_id")
   private List<Membership> memberList; // lista de relaciones usuario-sala

   @ManyToOne
   private User winner;

   public User checkWinner(){
      User winner = null;
      double maximum = 0;

      for (Membership member : memberList){
         if (winner == null || member.getBalance() > maximum){
            maximum = member.getBalance();
            winner = member.getUser();
         }
      }

      return winner;
   }
}
