package server.model.employee;

import lombok.Getter;
import lombok.Setter;
import server.model.prototype.EmployeeImagePrototype;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "EMPLOYEE_IMAGE")
public class EmployeeImage extends EmployeeImagePrototype implements Serializable {

    static final long serialVersionUID = 44L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "employee_id")
    @SequenceGenerator(name = "employee_id", sequenceName = "employee_id_sequence")
    private Long id;

    @Lob
    private byte[] image;
}
