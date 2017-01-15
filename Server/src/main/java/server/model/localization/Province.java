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
public class Province implements LocalizationComponent,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "province_id")
    @SequenceGenerator(name = "province_id", sequenceName = "province_id_sequence")
    private Long id;

    private ProvinceType provinceType;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Locality> localityList = new ArrayList<>();

    @Override
    public <S> void add(S object) {
        this.localityList.add((Locality) object);
    }
}

