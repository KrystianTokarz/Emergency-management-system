package server.gui.administrator.employeeManagement.employeesEditing;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import server.communication.EchoThread;
import server.gui.administrator.employeeManagement.EmployeeForTable;
import server.gui.administrator.employeeManagement.builder.EmployeeBuilder;
import server.gui.administrator.employeeManagement.builder.EmployeeBuilderImpl;
import server.gui.administrator.employeeManagement.builder.NewEmployeeBuildDirector;
import server.message.Message;
import server.message.mediator.CommandMediator;
import server.model.employee.Employee;
import server.model.message.MessageType;

import java.util.HashMap;
import java.util.Map;

public class AdministratorEmployeeEditService {

    private CommandMediator commandMediator;
    private ObservableList<EmployeeForTable> employeeForTables;
    private EmployeeForTable selectedEmployee;
    private Employee employeeFromServer = null;
    int index;


    public AdministratorEmployeeEditService(CommandMediator commandMediator, ObservableList<EmployeeForTable> employeeForTables, int index) {
        this.employeeForTables = employeeForTables;
        this.commandMediator = commandMediator;
        this.index = index;
        this.selectedEmployee = employeeForTables.get(index);
    }

    public Employee giveEmployeeFromServer(){
        return this.employeeFromServer;
    }

    public void editEmployee(String firstName, String lastName, String email, String type, String password, String login, ImageView image, Employee originalEmployee) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl();
        NewEmployeeBuildDirector newEmployeeBuildDirector = new NewEmployeeBuildDirector(employeeBuilder);
        Employee newEmployee = newEmployeeBuildDirector.construct(firstName, lastName, email,  type, password, login, image);
        Map<String , Employee> map = new HashMap<>();
        map.put("old",originalEmployee);
        map.put("new",newEmployee);
        selectedEmployee.setFirstName(firstName);
        selectedEmployee.setLastName(lastName);
        selectedEmployee.setType(type);
        selectedEmployee.setImage(image);
        selectedEmployee.setEmail(email);
        employeeForTables.set(index,selectedEmployee);
        Message message = new Message.MessageBuilder(MessageType.EDIT_EMPLOYEE)
                .object(map)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void setEmployeeFromServer(Employee employeeFromServer) {
        this.employeeFromServer = employeeFromServer;

    }
}
