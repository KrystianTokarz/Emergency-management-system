package server.gui.administrator.employeeManagement.builder;

import javafx.scene.image.ImageView;
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;

import java.time.LocalDate;

public class NewEmployeeBuildDirector {

    private EmployeeBuilder employeeBuilder;

    public  NewEmployeeBuildDirector(EmployeeBuilder employeeBuilder){
        this.employeeBuilder = employeeBuilder;
    }

    public Employee construct(String firstName, String lastName, String email, String type, String password, String login,ImageView image){
        employeeBuilder.setFirstName(firstName);
        employeeBuilder.setLastName(lastName);
        employeeBuilder.setEmail(email);
        employeeBuilder.setType(type);
        employeeBuilder.setLoginAndPassword(login,password);
        employeeBuilder.setImage(image);
        return employeeBuilder.getResult();
    }

}




