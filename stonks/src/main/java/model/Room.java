package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class room {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String title;
   

   @ManyToMany
   List<user> participants;
}
