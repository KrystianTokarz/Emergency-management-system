package server.model.employee;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;
import server.model.prototype.EmployeeAccountPrototype;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class EmployeeAccount extends EmployeeAccountPrototype implements Serializable{

    static final long serialVersionUID = 43L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "employee_account_id")
    @SequenceGenerator(name = "employee_account_id", sequenceName = "employee_account_id_sequence")
    private Long id;

    private String login;

    private String password;


}
