package server.gui.login;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.communication.EchoThread;
import server.gui.administrator.AdministratorService;
import server.gui.distributor.DistributorService;
import server.gui.distributor.observerReceivingPanel.ObservableConcrete;
import server.gui.distributor.receivingPanel.DistributorReceivingService;
import server.message.Message;
import server.message.mediator.CommandMediator;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;
import server.model.employee.EmployeeProfileType;
import server.model.message.MessageType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginService {
    private Message message;
    private Stage primaryStage;
    private CommandMediator commandMediator;
    private DistributorCommandMediator distributorCommandMediator;
    private Stage additionalStage = null;
    private  ObservableConcrete observableConcrete;
    private String language;
    private String country = new String("EN");
    private ResourceBundle resourceBundle;

    public LoginService(CommandMediator commandMediator,DistributorCommandMediator distributorCommandMediator){
        this.commandMediator = commandMediator;
        this.distributorCommandMediator = distributorCommandMediator;
        resourceBundle = loadResourceBundle();
    }

    private ResourceBundle loadResourceBundle() {
        if(country.equals("EN"))
            language = new String("en");
        else if(country.equals("PL"))
            language = new String("pl");
        Locale currentLocale = new Locale(language, country);
        File file = new File("src\\main\\resources\\internationalization");
        ClassLoader loader = null;


        try {
            URL[] urls =  new URL[]{ file.toURI().toURL()};
            loader = new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        resourceBundle = ResourceBundle.getBundle("MessageBundle",currentLocale,loader);
        return resourceBundle;
    }


    public void sendMessageToServer(Stage stage,String login, String password) {
        this.primaryStage = stage;
        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setLogin(login);
        employeeAccount.setPassword(password);
        message = new Message.MessageBuilder(MessageType.AUTHORIZATION)
                            .object(employeeAccount)
                            .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void showPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("LOGGING ERROR");
        alert.setContentText("Your login or password is wrong ! Try again :)");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public EmployeeProfileType getTypeOfEmployee(Employee employee) {
        return employee.getType();
    }

    public void loginIntoSystem(Object param){
        if (param == null) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showPopup();
            }
        });
    }   else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                        EmployeeProfileType employeeProfileType = getTypeOfEmployee((Employee) param);
                        ProgramViewFactory programViewFactory = new ProgramViewFactory();
                        Parent correctView = programViewFactory.getCorrectView(employeeProfileType, (Employee) param);

                        primaryStage.setScene(new Scene(correctView));
                        primaryStage.setX(300);
                        primaryStage.setY(150);
                        primaryStage.show();
                        if(additionalStage!= null) {


                            additionalStage.setX(primaryStage.getScene().getWindow().getX() + primaryStage.getScene().getWidth());
                            additionalStage.setY(primaryStage.getScene().getWindow().getY());
                            additionalStage.setAlwaysOnTop(true);
                            additionalStage.setOnCloseRequest(e -> {
                                e.consume();
                            });
                            additionalStage.setTitle("PANEL");
                            additionalStage.show();

                        }
                }
            });
        }
    }

    public void setResourceBundle(String resourceBundle) {
        this.country = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return loadResourceBundle();
    }

    /**
     * Embedded class for getting correct view for Employee (distributor or administrator view)
     */
    class ProgramViewFactory{
        public Parent getCorrectView(EmployeeProfileType type, Employee employee){
            Parent view;


            try {
                if(type == EmployeeProfileType.ADMINISTRATOR){
                    commandMediator.registerAdministratorService(new AdministratorService(commandMediator));
                    commandMediator.setProgramPanelInformation(employee);
                    view = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/administratorPanelView.fxml"));


                    return  view;
                }
                else if(type == EmployeeProfileType.DISTRIBUTOR){
                    observableConcrete= new ObservableConcrete();
                    additionalStage = new Stage();
                    distributorCommandMediator.registerDistributorReceivingService(new DistributorReceivingService(distributorCommandMediator,observableConcrete,additionalStage));
                    AnchorPane additionalView = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/receivingPanel/distributorReceivingPanelView.fxml"),resourceBundle);
                    additionalStage.setScene(new Scene(additionalView));
                    distributorCommandMediator.registerDistributorService(new DistributorService(distributorCommandMediator,observableConcrete));
                    distributorCommandMediator.setProgramPanelInformation(employee);
                    view = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/distributorPanelView.fxml"),resourceBundle);

                    return  view;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
