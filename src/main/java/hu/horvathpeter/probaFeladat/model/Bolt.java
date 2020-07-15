package hu.horvathpeter.probaFeladat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bolt {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nev;
    private int partnerId;

    @OneToMany(
            mappedBy = "bolt",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Vasarlas> vasarlas;

}
