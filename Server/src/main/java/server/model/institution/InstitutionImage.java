package server.model.institution;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "INSTITUTION_IMAGE")
public class InstitutionImage implements Serializable {

    static final long serialVersionUID = 44L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "institution_id")
    @SequenceGenerator(name = "institution_id", sequenceName = "institution_id_sequence")
    private Long id;

    @Lob
    private byte[] image;
}

