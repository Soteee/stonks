package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idsocio;

    @ManyToOne
    private user iduser;

    @ManyToOne
    private room idsala;

    private LocalDateTime joindate;
    private double balance;

    @OneToMany
    List<positions> plist = new ArrayList();



    
}
