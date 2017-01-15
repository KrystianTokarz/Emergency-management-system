package server.model.institution;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.Street;
import server.model.notification.Notification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "EMERGENCY_INSTITUTION")
public class Institution implements Serializable,Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institution_id")
    @SequenceGenerator(name = "institution_id", sequenceName = "institution_id_sequence")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_OF_INSTITUTION")
    private InstitutionType institutionType;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private InstitutionImage institutionImage;

    private String name;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Province province;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Locality locality;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Street street;

    private Boolean availability;

    @ManyToMany(mappedBy = "institutions")
    private List<Notification> notification = new ArrayList<>();

    @Override
    public  Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
