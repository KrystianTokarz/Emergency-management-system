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

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Employee employee;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Province province;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Locality locality;

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Street street;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="NOTIFICATION_TO_INSTITUTION",
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

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @PrePersist
    private void setTimeCreated() {
        this.timeCreated = Calendar.getInstance().getTime();
    }
}
