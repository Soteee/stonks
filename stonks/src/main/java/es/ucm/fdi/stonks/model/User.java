package es.ucm.fdi.stonks.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max=20)
    private String userName; // nombre de usuario; debería ser único (cómo lo hacemos?)

    @Size(max=40)
    private String realName; // nombre real

    @OneToMany
    @JoinColumn(name = "user_id")
    //salas de las que soy socio
    private List<Member> memberList; // lista de relaciones usuario-sala

    @Override
    public String toString() {

        return "ID: " + id + ", username: " + userName + ", real name: " + realName;

    }

}
