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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable{


    @FXML
    private TextField loginField = new TextField();

    @FXML
    private PasswordField passwordField = new PasswordField();

    @FXML
    private ComboBox i18lComboBox;

    @FXML
    private Button loginButton;

    @FXML
    private Stage primaryStage;

    private CommandMediator commandMediator;

    private DistributorCommandMediator distributorCommandMediator;

    private List<String> i18lNameArray;

    private  ResourceBundle resourcesBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator = CommandMediator.getInstance();
        distributorCommandMediator = DistributorCommandMediator.getInstance();
        commandMediator.registerLoginService(new LoginService(commandMediator,distributorCommandMediator));
        i18lNameArray = new LinkedList<>();
        i18lNameArray.add("EN");
        i18lNameArray.add("PL");
        i18lComboBox.getItems().addAll(i18lNameArray);
        i18lComboBox.getSelectionModel().select(0);

        resourcesBundle =  commandMediator.getResourceBundle();
        loginButton.setText(resourcesBundle.getString("login_button"));

        i18lComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)){
                commandMediator.setResourceBundle((String) newValue);
                resourcesBundle =  commandMediator.getResourceBundle();
                loginButton.setText(resourcesBundle.getString("login_button"));
            }
        });
    }

    @FXML
    public void handleButtonLogin(ActionEvent event){


        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        commandMediator.sendPasswordAndLogin(primaryStage,loginField.getText(), passwordField.getText());
    }

}
