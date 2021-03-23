package es.ucm.fdi.stonks.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Conversation conver; // conversación a la que pertenece

    @OneToOne
    private User sender; // usuario que envía el mensaje

    @NotNull
    @Size(max = 500)
    private String text; // cuerpo del mensaje

    @NotNull
    private LocalDateTime date; // fecha en la que se envía el mensaje
    
}
