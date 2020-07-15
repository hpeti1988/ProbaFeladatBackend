package hu.horvathpeter.probaFeladat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vasarlas {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date esemenyDatumIdo;

    private float vasarlasOsszeg;

    private int penztargepAzonosito;

    private int partnerId;

    @OneToMany(
            mappedBy = "vasarlas",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<VasarlasTetel> vasarlasTetel = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private Bolt bolt;

    @JsonIgnore
    public Bolt getBolt() {
        return bolt;
    }
    public String getBolt_nev() {
        return bolt.getNev();
    }

}
