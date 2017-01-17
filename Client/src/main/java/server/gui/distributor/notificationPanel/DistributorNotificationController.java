package server.gui.distributor.notificationPanel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import server.gui.distributor.notificationPanel.decorator.*;
import server.gui.distributor.receivingPanel.CallerForTable;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Street;
import server.model.message.MessageWithNotification;
import server.model.message.SecondMessageWithNotification;
import server.model.notification.AccidentType;
import sun.awt.windows.ThemeReader;


import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DistributorNotificationController implements Initializable {

    private DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField callerNumber;

    @FXML
    private ListView accidentListView;

    @FXML
    private ChoiceBox provinceChoiceBox;
    @FXML
    private ChoiceBox localityChoiceBox;
    @FXML
    private ChoiceBox streetChoiceBox;
    @FXML
    private ChoiceBox fireBrigadeChoiceBox;
    @FXML
    private ChoiceBox policeChoiceBox;
    @FXML
    private ChoiceBox emergencyChoiceBox;

    @FXML
    private TextField callerFirstNameTextField;
    @FXML
    private TextField callerLastNameTextField;

    @FXML
    private Button firstNotificationButton;

    @FXML
    private TextArea notationTextArea;
    @FXML
    private TextField numberOfVictimsTextField;

    @FXML
    private Button  secondNotificationButton;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int statusOfController = commandMediator.getStatusOfController();
        commandMediator.sendForInstitutionDataToEdit();
        String localization;
        if(statusOfController==0) {
            CallerForTable callerData = commandMediator.getCallerData();
            callerNumber.setText(callerData.getCallerPhoneNumber());
            localization = callerData.getCallerLocalization();
        }else{
            localization = null;
        }
        AccidentType[] enumConstants = AccidentType.INNE.getDeclaringClass().getEnumConstants();
        accidentListView.getItems().setAll(enumConstants);

        Task< List<String>> notificationTask = new Task< List<String>>(){

            @Override
            protected  List<String> call() throws Exception {
                List<String> provinceLists = null;
                do {
                    Thread.sleep(1000);
                    provinceLists = commandMediator.returnAllProvince();

                }while (provinceLists == null);
                return  provinceLists;
            }
        };


        notificationTask.setOnSucceeded(e-> {

                provinceChoiceBox.getItems().addAll(notificationTask.getValue());
            if(statusOfController==0) {
                String provinceForLocality = commandMediator.getProvinceForLocality(localization);
                provinceChoiceBox.getSelectionModel().select(provinceForLocality);

                List<Locality> localityForSelectedProvince = commandMediator.getLocalityForSelectedProvince(provinceChoiceBox.getValue().toString());


                localityChoiceBox.getSelectionModel().select(localization);
            }else{
                provinceChoiceBox.getSelectionModel().select(0);
                //localityChoiceBox.getSelectionModel().select(0);
            }



        });

        new Thread(notificationTask).start();

            provinceChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue.equals(oldValue)){
                    if(localityChoiceBox.getItems()!=null)
                        localityChoiceBox.getItems().clear();
                    List<Locality> localityForSelectedProvince = commandMediator.getLocalityForSelectedProvince((String) newValue);
                    for (Locality locality: localityForSelectedProvince) {
                        localityChoiceBox.getItems().add(locality.getLocality());
                    }
                    streetChoiceBox.getItems().clear();
                    streetChoiceBox.setDisable(true);
                    fireBrigadeChoiceBox.setDisable(true);
                    emergencyChoiceBox.setDisable(true);
                    policeChoiceBox.setDisable(true);
                }
            });

            localityChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(provinceChoiceBox.getValue() != null && newValue != null) {
                    List<Street> streetForSelectedLocality = commandMediator.getStreetForSelectedLocality((String) provinceChoiceBox.getValue(), (String) newValue);
                    streetChoiceBox.getItems().clear();
                    streetChoiceBox.setDisable(false);
                    for (Street street : streetForSelectedLocality) {
                        streetChoiceBox.getItems().add(street.getStreet() + " " + street.getSpecialNumber());
                    }
                }
                fireBrigadeChoiceBox.getItems().clear();
                emergencyChoiceBox.getItems().clear();
                policeChoiceBox.getItems().clear();
                fireBrigadeChoiceBox.setDisable(false);
                emergencyChoiceBox.setDisable(false);
                policeChoiceBox.setDisable(false);

                System.out.println("== " + newValue);
                List<Institution> rightInstitutionLForFireBrigade = commandMediator.getRightInstitutionListForProvinceAndLocality(InstitutionType.FIRE_BRIGADE,(String) newValue);
                List<Institution> rightInstitutionListForPolice = commandMediator.getRightInstitutionListForProvinceAndLocality(InstitutionType.POLICE,(String) newValue);
                List<Institution> rightInstitutionListForEmergency = commandMediator.getRightInstitutionListForProvinceAndLocality(InstitutionType.EMERGENCY,(String) newValue);
                fireBrigadeChoiceBox.getItems().add("No selected");
                emergencyChoiceBox.getItems().add("No selected");
                policeChoiceBox.getItems().add("No selected");

                for (Institution institution :rightInstitutionLForFireBrigade) {
                    fireBrigadeChoiceBox.getItems().add(institution.getName());
                }
                for (Institution institution :rightInstitutionListForEmergency) {
                    emergencyChoiceBox.getItems().add(institution.getName());
                }
                for (Institution institution :rightInstitutionListForPolice) {
                    policeChoiceBox.getItems().add(institution.getName());
                }
                fireBrigadeChoiceBox.getSelectionModel().select(0);
                emergencyChoiceBox.getSelectionModel().select(0);
                policeChoiceBox.getSelectionModel().select(0);
             });

        fireBrigadeChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(localityChoiceBox.getValue()!=null) {

            }
        });


    }

    public void showEmptyFieldPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("You leave empty field     ");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showErrorInput(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("You add bad data into number of victims ");
        alert.setHeaderText(null);
        alert.showAndWait();
    }



    @FXML
    public void sendSecondNotification(){
        String pattern = "\\d+";
        //System.out.println(myString.matches(pattern));
        if(numberOfVictimsTextField.getText()!="" && accidentListView.getSelectionModel().getSelectedIndex()!=-1) {
            if(!numberOfVictimsTextField.getText().matches(pattern))
                showErrorInput();
            else {
                SecondMessageWithNotification secondMessageWithNotification = new SecondMessageWithNotification();
                secondMessageWithNotification.setNumberOfVictims(Integer.parseInt(numberOfVictimsTextField.getText()));
                secondMessageWithNotification.setAccidentType((AccidentType) accidentListView.getSelectionModel().getSelectedItem());
                secondMessageWithNotification.setNotations(notationTextArea.getText());
                commandMediator.saveSecondNotification(secondMessageWithNotification);
                commandMediator.startThread();
                Stage stage = (Stage) secondNotificationButton.getScene().getWindow();
                stage.close();

            }
        }
    }

    @FXML
    public void sendFirstNotification(){

        if(localityChoiceBox.getSelectionModel().getSelectedIndex()!=-1 && streetChoiceBox.getSelectionModel().getSelectedIndex()!=-1
                &&(fireBrigadeChoiceBox.getSelectionModel().getSelectedIndex()!=0
                    || policeChoiceBox.getSelectionModel().getSelectedIndex()!=0 || emergencyChoiceBox.getSelectionModel().getSelectedIndex()!=0 )) {
            commandMediator.setInstitutionChoiceBoxForService(policeChoiceBox, emergencyChoiceBox, fireBrigadeChoiceBox);
            InstitutionNotification messageWithInstitution = messageWithInstitution = new PoliceInstitutionDecorator(new FireBrigadeInstitutionDecorator(new EmergencyInstitutionDecorator(new InstitutionNotificationImpl())));
            MessageWithNotification messageWithNotification = new MessageWithNotification();

            String firstName = callerFirstNameTextField.getText();
            String lastName = callerLastNameTextField.getText();
            String number = callerNumber.getText();


            String province = (String) provinceChoiceBox.getSelectionModel().getSelectedItem();

            String locality = (String) localityChoiceBox.getSelectionModel().getSelectedItem();
            String street = null;
            if (streetChoiceBox.getSelectionModel().getSelectedItem() != null)
                street = (String) streetChoiceBox.getSelectionModel().getSelectedItem();

            String[] split = street.split("\\s+");
            String streetName = split[0];
            String streetNumber = split[1];
            Employee employee = new Employee();
            employee.setEmail(commandMediator.getUserEmail());
            employee.setFirstName(commandMediator.getUserFirstName());
            employee.setLastName(commandMediator.getUserLastName());


            messageWithNotification.setCallerFirstNameTextField(firstName);
            messageWithNotification.setCallerLastNameTextField(lastName);
            messageWithNotification.setEmployee(employee);
            messageWithNotification.setCallerNumber(number);
            messageWithNotification.setProvince(province);
            messageWithNotification.setLocality(locality);
            messageWithNotification.setStreetName(streetName);
            messageWithNotification.setStreetNumber(streetNumber);
            messageWithNotification.setInstitutionNotification(messageWithInstitution.getInstitution());

            commandMediator.saveFirstNotification(messageWithNotification);
            provinceChoiceBox.setDisable(true);
            localityChoiceBox.setDisable(true);
            streetChoiceBox.setDisable(true);
            callerFirstNameTextField.setDisable(true);
            callerLastNameTextField.setDisable(true);
            callerNumber.setDisable(true);
            emergencyChoiceBox.setDisable(true);
            policeChoiceBox.setDisable(true);
            fireBrigadeChoiceBox.setDisable(true);
            firstNotificationButton.setDisable(true);
        }else{
            showEmptyFieldPopup();
        }
    }
}
