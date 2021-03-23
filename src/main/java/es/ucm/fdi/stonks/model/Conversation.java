package es.ucm.fdi.stonks.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user1; // usuario 1

    @ManyToOne
    private User user2; // usuario 2

    @OneToMany
    @JoinColumn (name = "message_id")
    private List<Message> messageList = new ArrayList<>(); // lista de mensajes

}
