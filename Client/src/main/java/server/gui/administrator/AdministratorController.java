package server.gui.administrator;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import server.gui.administrator.employeeManagement.AdministratorEmployeeManagementService;
import server.gui.administrator.institutionManagement.AdministratorInstitutionManagementService;
import server.message.mediator.CommandMediator;
import server.model.employee.EmployeeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    @FXML
    private Label userDataLabel = new Label();

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView userImageView;

    @FXML
    private Button institutionButton;

    @FXML
    private Button employeeButton;


    private PieChart pieChart;

    private Parent employeeViews = null;

    private Parent institutionViews = null;

    private CommandMediator commandMediator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator = CommandMediator.getInstance();
        userDataLabel.setText(commandMediator.loadEmployeeFirstAndLastName());
        byte[] imageArrayBytes = commandMediator.loadEmployeeImageView();
        if(imageArrayBytes == null){
            Path path = Paths.get("src\\main\\resources\\images\\employee-image.png");
            try {
                imageArrayBytes = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageArrayBytes);
            try {
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                userImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        ObservableList<PieChart.Data> pieCharData =   FXCollections.observableArrayList(
                new PieChart.Data("WYPADEK_DROGOWY",130),
                new PieChart.Data("POŻAR",150),
                new PieChart.Data("WYBUCH",11),
                new PieChart.Data("STRZELANINA",5));
        pieChart = new PieChart(pieCharData);
        pieChart.setTitle("STATISTIC");
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.BOTTOM);
        borderPane.setTop(pieChart);

        institutionButton.setDisable(false);
        employeeButton.setDisable(false);
    }

    @FXML
    public void logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You are sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            commandMediator.executeLogout();
        }
    }

    @FXML
    public void showEmployeeView(ActionEvent event) throws IOException, InterruptedException {
        if(institutionViews != null ) {
            Timeline timeline = commandMediator.give10SecondTimelineForInstitution();
            timeline.stop();
        }
        if(employeeViews != null ) {
            Timeline timeline = commandMediator.give5SecondTimelineForEmployee();
            timeline.stop();
        }


        commandMediator.sendMessageForEmployeeList();
        commandMediator.registerAdministratorEmployeeManagementService(new AdministratorEmployeeManagementService(commandMediator));
        employeeViews = FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/employeesPanel/administratorPanelEmployeeView.fxml"));
        borderPane.setTop(employeeViews);


    }

    public void showInstitutionView(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(employeeViews != null ) {
            Timeline timeline = commandMediator.give5SecondTimelineForEmployee();
            timeline.stop();
        }
        if(institutionViews != null){
            Timeline timeline = commandMediator.give10SecondTimelineForInstitution();
            timeline.stop();
        }
        commandMediator.sendMessageForInstitutionList();
        commandMediator.registerAdministratorInstitutionManagementService(new AdministratorInstitutionManagementService(commandMediator));
        institutionViews = FXMLLoader.load(getClass().getClassLoader().getResource("views/administrator/institutionsPanel/administratorPanelInstitutionView.fxml"));
        borderPane.setTop(institutionViews);


    }

    public void showSystemStatistic(ActionEvent actionEvent)
    {
        if(employeeViews != null ) {
            Timeline timeline = commandMediator.give5SecondTimelineForEmployee();
            timeline.stop();
        }
        if(institutionViews != null ) {
            Timeline timeline = commandMediator.give10SecondTimelineForInstitution();
            timeline.stop();
        }
        borderPane.setTop(pieChart);
    }
}
