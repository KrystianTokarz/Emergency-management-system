package server.model.message;

import lombok.Getter;
import lombok.Setter;
import server.model.employee.Employee;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class FirstMessageWithNotification implements Serializable {

    private List<String> institutionNotification;

    private String callerFirstNameTextField;

    private String callerLastNameTextField;

    private String callerNumber;

    private String province;

    private String locality;

    private String streetName;

    private String streetNumber;

    private Employee employee;


}
