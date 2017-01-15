package server.gui.administrator.employeeManagement;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.communication.EchoThread;
import server.gui.administrator.employeeManagement.flyweight.ImageFlyweight;
import server.gui.administrator.employeeManagement.flyweight.factory.ImageFlyweightFactory;
import server.message.Message;
import server.message.mediator.CommandMediator;
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;
import server.model.message.MessageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorEmployeeManagementService {

    private CommandMediator commandMediator;
    private List<Employee> employeeList = null;
    private ImageFlyweightFactory imageFlyweightFactory;
    private Timeline timeline = new Timeline();

    public AdministratorEmployeeManagementService(CommandMediator commandMediator) {
        this.commandMediator = commandMediator;
        this.imageFlyweightFactory = new ImageFlyweightFactory();
    }


    public void deleteEmployees(ObservableList<EmployeeForTable>  employeeList){

        List<Employee> listForServer = new ArrayList<>();

        for (EmployeeForTable emp : employeeList) {
            Employee employeeForServer = new Employee();
            employeeForServer.setFirstName(emp.getFirstName());
            employeeForServer.setLastName(emp.getLastName());
            employeeForServer.setEmail(emp.getEmail());
            listForServer.add(employeeForServer);
                    }
        Message message = new Message.MessageBuilder(MessageType.DELETE_EMPLOYEES)
                .object(listForServer)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void sendForEmployeeDataToEdit(Employee employee){
        Message message = new Message.MessageBuilder(MessageType.SEND_EMPLOYEE)
                .object(employee)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void setEmployeeList(Object employeeList){
        this.employeeList = (List<Employee>) employeeList;
    }

    public Timeline give5SecondsTimeline(){
        return timeline;
    }

    public ObservableList<EmployeeForTable> loadEmployeeTable(){

        if(employeeList == null)
            return null;

        ObservableList<EmployeeForTable>  employeesList= FXCollections.observableArrayList();
        for (Employee employee: employeeList) {
            EmployeeForTable employeeForTable = new EmployeeForTable();
            ImageFlyweight correctImageFlyweight;
            if(employee.getEmployeeImage()== null) {
                 correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(null);
            }
            else {
                 correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(employee.getEmployeeImage().getImage());
            }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(correctImageFlyweight.getCorrectEmployeeImage());
                try {
                    BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    employeeForTable.setImage(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
           // }
            //TUTAJ ZASTOSOWANO FLYWEIGHT
            employeeForTable.setFirstName(employee.getFirstName());
            employeeForTable.setLastName(employee.getLastName());
            employeeForTable.setEmail(employee.getEmail());
            if (employee.getType() == EmployeeProfileType.ADMINISTRATOR)
                employeeForTable.setType("Administrator");
            else
                employeeForTable.setType("Distributor");
            employeesList.add(employeeForTable);
        }
        return employeesList;
    }
}

