package hu.horvathpeter.probaFeladat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cikkek {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String cikkSzam;

    private String vonalKod;

    private String nev;

    private String mennyisegEgyseg;

    private float nettoEgysegar;

    private int verzio;

    private int partnerId;

    @OneToMany(
            mappedBy = "partnerct",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<VasarlasTetel> vasarlasTetel;

}
