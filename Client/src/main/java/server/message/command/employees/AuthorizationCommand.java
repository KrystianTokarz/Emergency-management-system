package server.message.command.employees;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.message.command.Command;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;

import java.io.IOException;

public class AuthorizationCommand implements Command {


    private Mediator commandMediator;

    public AuthorizationCommand(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerAuthorizationCommand(this);
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.executeLogin(param);
    }
}
