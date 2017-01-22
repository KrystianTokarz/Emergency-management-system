package server.gui.administrator.employeeManagement;

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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.gui.administrator.employeeManagement.employeesAdding.AdministratorEmployeeAddService;
import server.gui.administrator.employeeManagement.employeesEditing.AdministratorEmployeeEditService;
import server.message.mediator.CommandMediator;
import server.model.employee.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorEmployeeManagementController implements Initializable{

    @FXML
    private TableView<EmployeeForTable> table = new TableView<>();

    private CommandMediator commandMediator;

    private ObservableList<EmployeeForTable> employeeForTables = FXCollections.observableArrayList();

    private Stage addNewEmployeeStage;






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator = CommandMediator.getInstance();


        Task<ObservableList<EmployeeForTable>> employeeListTask = new Task<ObservableList<EmployeeForTable>>(){

            @Override
            protected ObservableList<EmployeeForTable> call() throws Exception {
                ObservableList<EmployeeForTable> employeeForTablesThread = null;
                        do {
                            Thread.sleep(1000);
                            employeeForTablesThread = commandMediator.loadEmployeeTable();
                        }while (employeeForTablesThread == null);
                return  employeeForTablesThread;
            }
        };


        employeeListTask.setOnSucceeded(e-> {
            employeeForTables.addAll(employeeListTask.getValue());
            table.setEditable(true);
            table.setItems(employeeForTables);
            table.refresh();
        });

        new Thread(employeeListTask).start();

        Timeline timeline = commandMediator.give5SecondTimelineForEmployee();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = table.getSelectionModel().getSelectedIndex();
                ObservableList<EmployeeForTable> employeeForTablesRefresh = null;
                commandMediator.sendMessageForEmployeeList();
                employeeForTablesRefresh = commandMediator.loadEmployeeTable();
                employeeForTables  = FXCollections.observableArrayList();
                employeeForTables.addAll(employeeForTablesRefresh);
                table.setItems(employeeForTables);
                table.refresh();
                table.getSelectionModel().select(selectedIndex);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }



    @FXML
    public void addNewEmployee(ActionEvent event)throws IOException {
        AnchorPane employeeViews =FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/employeesPanel/administratorPanelAddingEmployeeView.fxml"));
        addNewEmployeeStage = new Stage();
        addNewEmployeeStage.setScene(new Scene(employeeViews));
        addNewEmployeeStage.show();
        commandMediator.registerAdministratorEmployeeAddService(new AdministratorEmployeeAddService(commandMediator));
    }

    @FXML
    public void deleteEmployee(ActionEvent event)throws IOException{
        if(table.getSelectionModel().getSelectedItem()!= null) {
            ObservableList<EmployeeForTable> employeeForDelete;
            employeeForDelete = table.getSelectionModel().getSelectedItems();
            commandMediator.deleteEmployeesFromTable(employeeForDelete);
            employeeForDelete.forEach(employeeForTables::remove);
            table.refresh();
        }
    }

    public void editEmployee(ActionEvent event)throws IOException {
        if(table.getSelectionModel().getSelectedItem()!= null) {
            commandMediator.registerAdministratorEmployeeEditService(new AdministratorEmployeeEditService(commandMediator, employeeForTables, table.getSelectionModel().getSelectedIndex()));
            Employee employee = new Employee();
            employee.setFirstName(table.getSelectionModel().getSelectedItem().getFirstName());
            employee.setLastName(table.getSelectionModel().getSelectedItem().getLastName());
            employee.setEmail(table.getSelectionModel().getSelectedItem().getEmail());
            commandMediator.sendForEmployeeDataToEdit(employee);
            AnchorPane employeeViews = FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/employeesPanel/administratorPanelEditingEmployeeView.fxml"));
            addNewEmployeeStage = new Stage();
            addNewEmployeeStage.setScene(new Scene(employeeViews));
            addNewEmployeeStage.show();
        }
    }
}
