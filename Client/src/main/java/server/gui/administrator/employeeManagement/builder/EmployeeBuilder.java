package server.gui.administrator.employeeManagement.builder;

import javafx.scene.image.ImageView;
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;

import java.time.LocalDate;


/**
 * Builder pattern interface for build employee
 */
public interface EmployeeBuilder {

    Employee getResult();
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmail(String email);
    void setType(String type);
    void setLoginAndPassword(String login, String password);
    void setImage(ImageView image);
}
