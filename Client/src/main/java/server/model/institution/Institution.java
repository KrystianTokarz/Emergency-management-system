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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Province province;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Locality locality;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Street street;

    private Boolean availability;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Notification> notification = new ArrayList<>();


    @Override
    public  Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Institution that = (Institution) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (institutionType != that.institutionType) return false;
        if (institutionImage != null ? !institutionImage.equals(that.institutionImage) : that.institutionImage != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (availability != null ? !availability.equals(that.availability) : that.availability != null) return false;
        return notification != null ? notification.equals(that.notification) : that.notification == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (institutionType != null ? institutionType.hashCode() : 0);
        result = 31 * result + (institutionImage != null ? institutionImage.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        result = 31 * result + (notification != null ? notification.hashCode() : 0);
        return result;
    }
}
