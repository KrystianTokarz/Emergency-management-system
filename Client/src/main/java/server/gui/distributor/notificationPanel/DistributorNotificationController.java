package server.gui.distributor.notificationPanel;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.gui.distributor.notificationPanel.decorator.*;
import server.gui.distributor.receivingPanel.CallerForTable;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Street;
import server.model.message.MessageWithNotification;
import server.model.notification.AccidentType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DistributorNotificationController implements Initializable {

    private DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator.sendForInstitutionDataToEdit();
        CallerForTable callerData = commandMediator.getCallerData();
        callerNumber.setText(callerData.getCallerPhoneNumber());
        String localization = callerData.getCallerLocalization();

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
            String provinceForLocality = commandMediator.getProvinceForLocality(localization);
            provinceChoiceBox.getSelectionModel().select(provinceForLocality);

            List<Locality> localityForSelectedProvince = commandMediator.getLocalityForSelectedProvince(provinceChoiceBox.getValue().toString());


            localityChoiceBox.getSelectionModel().select(localization);




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

    @FXML
    public void sendFirstNotification(){



        commandMediator.setInstitutionChoiceBoxForService(policeChoiceBox,emergencyChoiceBox,fireBrigadeChoiceBox);
        InstitutionNotification messageWithInstitution =  messageWithInstitution = new PoliceInstitutionDecorator(new FireBrigadeInstitutionDecorator(new EmergencyInstitutionDecorator(new InstitutionNotificationImpl())));
        MessageWithNotification messageWithNotification = new MessageWithNotification();

        String firstName = callerFirstNameTextField.getText();
        String lastName = callerLastNameTextField.getText();
        String number = callerNumber.getText();


        String province = (String)provinceChoiceBox.getSelectionModel().getSelectedItem();

        String locality  = (String) localityChoiceBox.getSelectionModel().getSelectedItem();
        String street = null;
        if(streetChoiceBox.getSelectionModel().getSelectedItem() != null)
            street = (String) streetChoiceBox.getSelectionModel().getSelectedItem();

        String[] split = street.split(" ");
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
        System.out.println("wykonano");
    }
}
