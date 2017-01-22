package server.message.command.employees;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;

import java.io.IOException;

public class AuthorizationCommand implements Command {




    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.executeLogin();
    }
}
