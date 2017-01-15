package server.model.employee;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;
import server.model.prototype.EmployeePrototype;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Employee extends EmployeePrototype implements Serializable{

    static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "employee_id")
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_id_sequence")
    private Long id;

    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(nullable = false, name="LAST_NAME")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EmployeeImage employeeImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EmployeeAccount employeeAccount;

    private String email;

    @Column(name="TYPE", length=15, nullable=false)
    @Enumerated(EnumType.STRING)
    private EmployeeProfileType type;

//    @OneToMany
//    private List<Notification> notifications;


}
