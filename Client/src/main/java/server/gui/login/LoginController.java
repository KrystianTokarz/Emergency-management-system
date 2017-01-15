package server.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import server.message.mediator.CommandMediator;
import server.message.mediator.DistributorCommandMediator;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {


    @FXML
    private TextField loginField = new TextField();

    @FXML
    private PasswordField passwordField = new PasswordField();

    @FXML
    private Stage primaryStage;

    private CommandMediator commandMediator;

    private DistributorCommandMediator distributorCommandMediator;

    @FXML
    public void handleButtonLogin(ActionEvent event){
        commandMediator = CommandMediator.getInstance();
        distributorCommandMediator = DistributorCommandMediator.getInstance();
        commandMediator.registerLoginService(new LoginService(commandMediator,distributorCommandMediator));
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        commandMediator.sendPasswordAndLogin(primaryStage,loginField.getText(), passwordField.getText());
    }



}
