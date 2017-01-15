package server.gui.administrator.institutionManagement;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.gui.administrator.institutionManagement.institutionState.StateEditing;
import server.gui.administrator.institutionManagement.institutionState.StateContext;
import server.gui.administrator.institutionManagement.institutionState.AdministratorInstitutionOperationService;
import server.gui.administrator.institutionManagement.institutionState.StateAdding;
import server.message.mediator.CommandMediator;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.localization.Locality;
import server.model.localization.ProvinceType;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdministratorInstitutionManagementController implements Initializable {

    @FXML
    private TableView<InstitutionForTable> table = new TableView<>();

    @FXML
    private ComboBox provinceComboBox = new ComboBox();
    @FXML
    private ComboBox localityComboBox = new ComboBox();
    @FXML
    private ComboBox institutionTypeComboBox = new ComboBox();

    private CommandMediator commandMediator;

    private ObservableList<InstitutionForTable> institutionForTables = FXCollections.observableArrayList();

    private ObservableList<InstitutionForTable> convertedInstitutionForTables = null ;

    private ObservableList<InstitutionForTable> moreConvertedInstitutionForTables = null;

    private Stage addNewInstitutionStage;

    private StateContext administratorManagementStateContext;

    private int sizeOfElement ;


    private int valueSelectedView = 0;


    public void showInformationPopup(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setContentText("Somebody introduces changes into Institutions! System must be reload");
        alert.setHeaderText(null);
        alert.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator = CommandMediator.getInstance();




        localityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!= null) {

//                if(moreConvertedInstitutionForTables== null) {
//                    moreConvertedInstitutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(
//                            commandMediator.getRightInstitutionListForLocality(
//                                    convertedInstitutionForTables, (String) newValue
//                            ));
//                }else{
//                    moreConvertedInstitutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(
//                            commandMediator.getRightInstitutionListForLocality(
//                                    moreConvertedInstitutionForTables, (String) newValue
//                            ));
//                }
                String provinceComboBoxValue = (String) provinceComboBox.getValue();
                String typeComboBoxValue = (String) institutionTypeComboBox.getValue();

                if(provinceComboBoxValue == null)
                    provinceComboBoxValue = "All";
                if(typeComboBoxValue == null)
                    typeComboBoxValue = "All";

                moreConvertedInstitutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(
                        commandMediator.getRightInstitutionListForType(typeComboBoxValue,provinceComboBoxValue,(String) newValue));
                table.setItems(moreConvertedInstitutionForTables);
                table.refresh();
            }

        });


        institutionTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if((oldValue != null) && (oldValue.equals(newValue)))
                return;
            else {

                String provinceComboBoxValue = (String) provinceComboBox.getValue();
                String localityComboBoxValue = (String) localityComboBox.getValue();
                if(provinceComboBoxValue == null)
                    provinceComboBoxValue = "All";
                if(localityComboBoxValue == null)
                    provinceComboBoxValue = "All";



                moreConvertedInstitutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(
                        commandMediator.getRightInstitutionListForType((String) newValue,provinceComboBoxValue,localityComboBoxValue));
                table.setItems(moreConvertedInstitutionForTables);
                table.refresh();

            }
        });



        provinceComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {

            localityComboBox.setItems(null);
            if((oldValue != null) && (oldValue.equals(newValue)))
                return;
            else {
                localityComboBox.setDisable(false);
                institutionForTables.clear();
                List<Institution> rightInstitutionList = null;
                if(newValue.equals("All")) {
                    localityComboBox.setDisable(true);
                    rightInstitutionList = commandMediator.getRightInstitutionList(null);

                }else {
                    if (newValue.equals("Swietokrzyskie")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.SWIETOKRZYSKIE);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            ObservableList<String> nameOfLocality = FXCollections.observableArrayList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }

                            localityComboBox.setItems(nameOfLocality);
                        }
                    } else if (newValue.equals("Lodzkie")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.LODZKIE);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            ObservableList<String> nameOfLocality = FXCollections.observableArrayList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }

                            localityComboBox.setItems(nameOfLocality);
                        }
                    } else if (newValue.equals("Maslovia")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.MASLOVIA);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            ObservableList<String> nameOfLocality = FXCollections.observableArrayList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }
                            localityComboBox.setItems(nameOfLocality);
                        }
                    }
                    if(localityComboBox.getItems()!=null)
                        localityComboBox.getItems().add(0,"All");
                    else
                        localityComboBox.setDisable(true);
                }


                convertedInstitutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(rightInstitutionList);
                String valueInstitutionType = (String) institutionTypeComboBox.getValue();
                if(valueInstitutionType!=null && !valueInstitutionType.equals("All"))
                    convertedInstitutionForTables = commandMediator.sortWithInstitutionType(convertedInstitutionForTables,valueInstitutionType);
                table.setItems(convertedInstitutionForTables);
            }
        });

        Task<ObservableList<InstitutionForTable>> institutionListTask = new Task<ObservableList<InstitutionForTable>>(){

            @Override
            protected ObservableList<InstitutionForTable> call() throws Exception {
                ObservableList<InstitutionForTable> institutionListTask = null;
                do {
                    Thread.sleep(1000);
                    institutionListTask = commandMediator.loadInstitutionTable();

                }while (institutionListTask == null);
                return  institutionListTask;
            }
        };


        institutionListTask.setOnSucceeded(e-> {
            institutionForTables.addAll(institutionListTask.getValue());
            sizeOfElement = institutionForTables.size();
            table.setEditable(true);
            table.setItems(institutionForTables);
            table.refresh();
        });

        new Thread(institutionListTask).start();





        Timeline timeline=commandMediator.give10SecondTimelineForInstitution();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                int selectedIndex = table.getSelectionModel().getSelectedIndex();
                commandMediator.sendMessageForInstitutionList();
                String provinceItem = (String) provinceComboBox.getSelectionModel().getSelectedItem();
                String typeItem = (String) institutionTypeComboBox.getSelectionModel().getSelectedItem();
                if(typeItem==null)
                    typeItem = "All";
                String localityItem = (String) localityComboBox.getSelectionModel().getSelectedItem();
                institutionForTables = FXCollections.observableArrayList();
                List<Institution> rightInstitutionListForType = commandMediator.getRightInstitutionListForType(typeItem, provinceItem, localityItem);
                institutionForTables = commandMediator.convertInstitutionListIntoIntitutionForTableList(rightInstitutionListForType);
                table.setItems(institutionForTables);
                table.refresh();
                table.getSelectionModel().select(selectedIndex);
            }
        }));


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadSelectedView() throws IOException {
        commandMediator.registerAdministratorInstitutionAddService(new AdministratorInstitutionOperationService(commandMediator,administratorManagementStateContext,valueSelectedView,table));
        AnchorPane institutionOperationView = FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/institutionsPanel/administratorPanelOperationInstitutionView.fxml"));
        addNewInstitutionStage = new Stage();
        addNewInstitutionStage.setScene(new Scene(institutionOperationView));
        addNewInstitutionStage.show();
    }
    @FXML
    public void addNewInstitution(ActionEvent event)throws IOException {
        administratorManagementStateContext = new StateContext(new StateAdding());
        valueSelectedView=1;
        loadSelectedView();
    }

    @FXML
    public void editInstitution(ActionEvent event)throws IOException {
        if(table.getSelectionModel().getSelectedItem()!= null) {
            Institution institution = new Institution();
            institution.setName(table.getSelectionModel().getSelectedItem().getName());
            commandMediator.sendForInstitutionDataToEdit(institution);
            administratorManagementStateContext = new StateContext(new StateEditing());
            valueSelectedView=2;
            loadSelectedView();
        }
    }

    @FXML
    public void deleteInstitution(ActionEvent event)throws IOException {
        if(table.getSelectionModel().getSelectedItem()!= null) {
            ObservableList<InstitutionForTable> institutionForDelete;
            institutionForDelete = table.getSelectionModel().getSelectedItems();
            commandMediator.deleteInstitutionFromTable(institutionForDelete);
            institutionForDelete.forEach(institutionForTables::remove);
            table.refresh();
        }
    }


}
