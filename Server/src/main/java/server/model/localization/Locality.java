package server.model.localization;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Locality implements LocalizationComponent,Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locality_id")
    @SequenceGenerator(name = "locality_id", sequenceName = "locality_id_sequence")
    private Long id;

    private String locality;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Street> streetList = new ArrayList<>();



    @Override
    public <S> void add(S object) {
        this.streetList.add((Street) object);
    }

}
