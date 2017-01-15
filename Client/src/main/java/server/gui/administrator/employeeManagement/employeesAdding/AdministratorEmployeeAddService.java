package server.gui.administrator.employeeManagement.employeesAdding;

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

public class AdministratorEmployeeAddService {

    private CommandMediator commandMediator;
    private ObservableList<EmployeeForTable> employeeForTables;


    public AdministratorEmployeeAddService(CommandMediator commandMediator) {
        this.commandMediator = commandMediator;
    }



    public EmployeeForTable addEmployeeForTable(String firstName, String lastName, String email, String type,ImageView image){
        EmployeeForTable employeeForTable = new EmployeeForTable();
        employeeForTable.setFirstName(firstName);
        employeeForTable.setLastName(lastName);
        employeeForTable.setEmail(email);
        employeeForTable.setType(type);
        employeeForTable.setAvailability(false);
        employeeForTable.setImage(image);
        return employeeForTable;
    }



    public void saveNewEmployee(String firstName, String lastName, String email, String type, String password, String login,ImageView image) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilderImpl();
        NewEmployeeBuildDirector newEmployeeBuildDirector = new NewEmployeeBuildDirector(employeeBuilder);
        Employee newEmployee = newEmployeeBuildDirector.construct(firstName,
                lastName,
                email,
                type,
                password,
                login,
                image);


        Message message = new Message.MessageBuilder(MessageType.SAVE_NEW_EMPLOYEE)
                .object(newEmployee)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }


}
