package server.model.notification;


import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.Street;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id")
    @SequenceGenerator(name = "notification_id", sequenceName = "notification_id_sequence")
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    private Employee employee;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Province province;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Locality locality;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Street street;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} ,fetch = FetchType.EAGER, mappedBy = "notification")
    @JoinTable(name="notification_for_institution",
            joinColumns = {@JoinColumn(name="NOTIFICATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "INSTITUTION_ID")}
        )
    private List<Institution> institutions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AccidentType accidentType;

    private int numberOfVictims;

    private int status;

    private String callerPhoneNumber;

    private String callerFirstName;

    private String callerLastName;

    private String notation;

    private Boolean reportBoat;

    private Boolean reportHelicopter;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @PrePersist
    private void setTimeCreated() {
        this.timeCreated = Calendar.getInstance().getTime();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (numberOfVictims != that.numberOfVictims) return false;
        if (status != that.status) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (institutions != null ? !institutions.equals(that.institutions) : that.institutions != null) return false;
        if (accidentType != that.accidentType) return false;
        if (callerPhoneNumber != null ? !callerPhoneNumber.equals(that.callerPhoneNumber) : that.callerPhoneNumber != null)
            return false;
        if (callerFirstName != null ? !callerFirstName.equals(that.callerFirstName) : that.callerFirstName != null)
            return false;
        if (callerLastName != null ? !callerLastName.equals(that.callerLastName) : that.callerLastName != null)
            return false;
        if (notation != null ? !notation.equals(that.notation) : that.notation != null) return false;
        if (reportBoat != null ? !reportBoat.equals(that.reportBoat) : that.reportBoat != null) return false;
        if (reportHelicopter != null ? !reportHelicopter.equals(that.reportHelicopter) : that.reportHelicopter != null)
            return false;
        return timeCreated != null ? timeCreated.equals(that.timeCreated) : that.timeCreated == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (institutions != null ? institutions.hashCode() : 0);
        result = 31 * result + (accidentType != null ? accidentType.hashCode() : 0);
        result = 31 * result + numberOfVictims;
        result = 31 * result + status;
        result = 31 * result + (callerPhoneNumber != null ? callerPhoneNumber.hashCode() : 0);
        result = 31 * result + (callerFirstName != null ? callerFirstName.hashCode() : 0);
        result = 31 * result + (callerLastName != null ? callerLastName.hashCode() : 0);
        result = 31 * result + (notation != null ? notation.hashCode() : 0);
        result = 31 * result + (reportBoat != null ? reportBoat.hashCode() : 0);
        result = 31 * result + (reportHelicopter != null ? reportHelicopter.hashCode() : 0);
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        return result;
    }
}
