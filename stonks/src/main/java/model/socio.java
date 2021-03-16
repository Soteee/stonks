package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idsocio;

    @ManyToOne
    private long iduser;

    @ManyToOne
    private long idsala;

    private LocalDateTime joindate;
    private double balance;

    @OneToMany
    List<positions> plist = new ArrayList();



    
}
