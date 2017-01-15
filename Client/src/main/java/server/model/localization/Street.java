package server.model.localization;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Street implements LocalizationComponent,Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "street_id")
    @SequenceGenerator(name = "street_id", sequenceName = "street_id_sequence")
    private Long id;

    private String street;

    private String specialNumber;

    @Override
    public <S> void add(S object) {

    }
}
