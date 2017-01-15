package server.gui.administrator.employeeManagement.builder;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;
import server.model.employee.EmployeeImage;
import server.model.employee.EmployeeProfileType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class EmployeeBuilderImpl implements  EmployeeBuilder {

    private Employee employee;

    public EmployeeBuilderImpl(){
        this.employee = new Employee();
    }

    public void setFirstName(String firstName){
        employee.setFirstName(firstName);
    }

    public void setLastName(String lastName){
        employee.setLastName(lastName);
    }

    public void setEmail(String email){
        employee.setEmail(email);
    }

    public void setType(String type){
        if(type.equals("Administrator"))
            employee.setType(EmployeeProfileType.ADMINISTRATOR);
        else if(type.equals("Distributor"))
            employee.setType(EmployeeProfileType.DISTRIBUTOR);

    }

    public void setLoginAndPassword(String login, String password){
        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setLogin(login);
        employeeAccount.setPassword(password);
        employee.setEmployeeAccount(employeeAccount);
    }


    @Override
    public void setImage(ImageView image) {
        if( image.getImage() != null) {
            byte[] byteArrayImage = null;
            BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            try {
                ImageIO.write(bImage, "png", s);
                byteArrayImage = s.toByteArray();
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            EmployeeImage employeeImage = new EmployeeImage();
            employeeImage.setImage(byteArrayImage);
            employee.setEmployeeImage(employeeImage);
        }
        else
            employee.setEmployeeImage(null);
    }

    public Employee getResult(){
        return this.employee;
    }
}
