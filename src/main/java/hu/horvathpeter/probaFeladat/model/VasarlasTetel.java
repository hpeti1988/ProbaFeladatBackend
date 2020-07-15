package hu.horvathpeter.probaFeladat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VasarlasTetel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Cikkek partnerct;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Vasarlas vasarlas;

    public int getVasarlas_id() {
        return vasarlas.getId();
    }

    public int getCikk_id() {
        return partnerct.getId();
    }

    private float mennyiseg;

    private float brutto;

    private int partnerId;

}
