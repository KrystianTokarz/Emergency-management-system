package server.gui.distributor.employeeData;

import javafx.scene.image.ImageView;
import server.communication.EchoThread;
import server.gui.administrator.employeeManagement.builder.EmployeeBuilder;
import server.gui.administrator.employeeManagement.builder.EmployeeBuilderImpl;
import server.gui.administrator.employeeManagement.builder.NewEmployeeBuildDirector;
import server.message.Message;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.message.MessageType;

import java.util.HashMap;
import java.util.Map;

public class DistributorEmployeeEditService {

    private DistributorCommandMediator commandMediator;
    private Employee employeeFromServer = null;


    public DistributorEmployeeEditService(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }

    public void setEmployeeFromServer(Employee employee){
        this.employeeFromServer = employee;
    }

    public Employee giveEmployeeFromServer(){
        return this.employeeFromServer;
    }

    public void sendDataToEditEmployee(String firstName, String lastName, String email, String password, String login, ImageView image, Employee originalEmployee) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl();
        NewEmployeeBuildDirector newEmployeeBuildDirector = new NewEmployeeBuildDirector(employeeBuilder);
        Employee newEmployee = newEmployeeBuildDirector.construct(firstName, lastName, email, "Distributor", password, login, image);
        Map<String , Employee> map = new HashMap<>();
        map.put("old",originalEmployee);
        map.put("new",newEmployee);
        Message message = new Message.MessageBuilder(MessageType.EDIT_EMPLOYEE_DISTRIBUTOR)
                .object(map)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }


}
