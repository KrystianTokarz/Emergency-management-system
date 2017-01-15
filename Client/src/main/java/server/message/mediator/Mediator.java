package server.message.mediator;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.gui.administrator.AdministratorService;
import server.gui.administrator.employeeManagement.EmployeeForTable;
import server.gui.administrator.employeeManagement.AdministratorEmployeeManagementService;
import server.gui.administrator.employeeManagement.employeesAdding.AdministratorEmployeeAddService;
import server.gui.administrator.employeeManagement.employeesEditing.AdministratorEmployeeEditService;
import server.gui.administrator.institutionManagement.AdministratorInstitutionManagementService;
import server.gui.login.LoginService;
import server.message.command.employees.AuthorizationCommand;
import server.message.command.employees.OneEmployeeCommand;
import server.message.command.institutions.OneInstitutionCommand;
import server.model.employee.Employee;
import server.model.institution.Institution;

public interface Mediator {
     void registerAuthorizationCommand(AuthorizationCommand authorizationCommand);
     void registerOneEmployeeCommand(OneEmployeeCommand oneEmployeeCommand);
     void registerLoginService(LoginService loginService);
     void registerAdministratorService(AdministratorService administratorService);
     void registerAdministratorEmployeeManagementService(AdministratorEmployeeManagementService employeeManagementService);
     void registerAdministratorInstitutionManagementService(AdministratorInstitutionManagementService administratorInstitutionManagementService);
     void executeLogin(Object object);
     void executeLogout();
     void sendPasswordAndLogin(Stage stage, String login, String password);
     void setProgramPanelInformation(Employee employee);
     String loadEmployeeFirstAndLastName();
     void sendMessageForEmployeeList();
     void setEmployeeList(Object param);
     void deleteEmployeesFromTable(ObservableList<EmployeeForTable> employeeForTable);
     void registerAdministratorEmployeeAddService(AdministratorEmployeeAddService service);
     void registerAdministratorEmployeeEditService(AdministratorEmployeeEditService service);
     void sendForEmployeeDataToEdit(Employee employee);
     void sendDataToEditEmployee(String firstName, String lastName, String email, String type, String password, String login, ImageView image, Employee oryginalEmployee);
     void giveEmployeeData(Employee param);
     Employee giveEmployeeFromServer();
     void setInstitutionFromServer(Institution institution);

    void sendMessageForInstitutionList();

     void setAllInstitutionList(Object param);

     void registerOneInstitutionCommand(OneInstitutionCommand oneInstitutionCommand);
}
