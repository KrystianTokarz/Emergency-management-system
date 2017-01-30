package server.gui.distributor;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import server.gui.distributor.builder.NotificationForDistributorTables;
import server.gui.distributor.builder.NotificationForTable;
import server.gui.distributor.employeeData.DistributorEmployeeEditService;
import server.gui.distributor.phone.DistributorPhoneService;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.notification.Notification;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public class DistributorController implements Initializable {

    @FXML
    private Label userDataLabel;

    @FXML
    private Label distributorPanelLabel;
    @FXML
    private Label yourNotificationLabel;
    @FXML
    private Label notificationInSystemLabel;
    @FXML
    private Button editButton;

    @FXML
    private ImageView userImageView;

    @FXML
    private Button googleButton;

    @FXML
    private Button antiStormButton;

    @FXML
    private Button phoneButton;

    private Stage phoneStage;

    @FXML
    private Pane googleMapPane;
    @FXML
    private Label clockLabel;

    @FXML
    private Button attentionButton;

    @FXML
    private ToggleButton breakOnButton;

    @FXML
    private TableView tableWithUserNotifications;

    @FXML
    private TableView tableWithAllSystemNotifications;

    @FXML
    private ToggleButton breakOffButton;

    private ToggleGroup toggleGroup;

    private DistributorCommandMediator commandMediator;

    private ObservableList<NotificationForDistributorTables> notificationForUserTablesList = FXCollections.observableArrayList();

    private ObservableList<NotificationForDistributorTables> notificationForAllApplicationTablesList = FXCollections.observableArrayList();

    private Parent employeeEditViews = null;

    private ResourceBundle resourceBundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandMediator = DistributorCommandMediator.getInstance();
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

        resourceBundle = resources;
        loadInternationalizationNames();
        Timeline timelineForNotificationInApplication=commandMediator.give4SecondTimelineForNotificationInApplication();
        timelineForNotificationInApplication.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int selectedIndexForAllNotifications = tableWithAllSystemNotifications.getSelectionModel().getSelectedIndex();
                int selectedIndexForUserNotifications = tableWithAllSystemNotifications.getSelectionModel().getSelectedIndex();
                commandMediator.sendMessageForAllNotification();
                notificationForAllApplicationTablesList = FXCollections.observableArrayList();
                List<Notification> notificationForAllApplicationTable = commandMediator.getNotificationForAllApplicationTable();
                notificationForUserTablesList = FXCollections.observableArrayList();
                List<Notification> notificationForUserTable = commandMediator.getNotificationForUserTable();

                if(notificationForAllApplicationTable!=null && notificationForUserTable!=null) {
                    for (Notification notification : notificationForAllApplicationTable) {
                        System.out.println(notification.getInstitutions());
                        NotificationForDistributorTables forUser = new NotificationForTable.NotificationBuilder(notification.getAccidentType(),
                                notification.getTimeCreated())
                                .institutionType(notification.getInstitutions())
                                .distributorData(notification.getEmployee().getFirstName(), notification.getEmployee().getLastName())
                                .status(notification.getStatus())
                                .callerData(notification.getCallerFirstName(),notification.getCallerLastName())
                                .callerNumber(notification.getCallerPhoneNumber())
                                .city(notification.getLocality().getLocality())
                                .build();
                        notificationForAllApplicationTablesList.add(forUser);
                    }
                    for (Notification notification : notificationForUserTable) {
                        NotificationForDistributorTables forUser = new NotificationForTable.NotificationBuilder(notification.getAccidentType(),
                                notification.getTimeCreated())
                                .institutionType(notification.getInstitutions())
                                .distributorData(notification.getEmployee().getFirstName(), notification.getEmployee().getLastName())
                                .status(notification.getStatus())
                                .build();

                        notificationForUserTablesList.add(forUser);
                    }
                    tableWithUserNotifications.setItems(notificationForUserTablesList);
                    tableWithUserNotifications.refresh();
                    tableWithUserNotifications.getSelectionModel().select(selectedIndexForUserNotifications);
                    tableWithAllSystemNotifications.setItems(notificationForAllApplicationTablesList);
                    tableWithAllSystemNotifications.refresh();
                }
            }
        }));
        timelineForNotificationInApplication.setCycleCount(Timeline.INDEFINITE);
        timelineForNotificationInApplication.play();

        Image googleImage = new Image("images/world-web.png",20,20,false,false);
        Image antiStormImage = new Image("images/weather.png",20,20,false,false);
        Image phoneImage = new Image("images/phone.png",25,20,false,false);
        Image attentionImage = new Image("images/attention.png",50,50,false,false);


        Timeline timelineForSatellite = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
                                String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
                                String secondString = StringUtilities.pad(2, '0', time.get(Calendar.SECOND) + "");
                                String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                                clockLabel.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timelineForSatellite.setCycleCount(Animation.INDEFINITE);
        timelineForSatellite.play();

        googleButton.setGraphic(new ImageView(googleImage));
        googleButton.setTooltip(new Tooltip("www.google.com"));
        antiStormButton.setGraphic(new ImageView(antiStormImage));
        antiStormButton.setTooltip(new Tooltip("www.antistorm.eu"));
        phoneButton.setGraphic(new ImageView(phoneImage));
        attentionButton.setGraphic(new ImageView(attentionImage));
        attentionButton.setTooltip(new Tooltip("Click to activate the emergency alarm"));

        toggleGroup = new ToggleGroup();
        breakOnButton.setToggleGroup(toggleGroup);
        breakOffButton.setToggleGroup(toggleGroup);

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("googleMap/googlemap.html").toString());
        BorderPane root = commandMediator.createGoogleMap(webEngine);
        root.setCenter(webView);
        googleMapPane.getChildren().setAll(root);
    }

    public void activeEmergencyAlarm(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You are sure to active emergency ALARM ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            commandMediator.activeEmergencyAlarm();
        }
    }

    public void loadInternationalizationNames(){
        distributorPanelLabel.setText(resourceBundle.getString("panel_name"));
        yourNotificationLabel.setText(resourceBundle.getString("first_notification"));
        notificationInSystemLabel.setText(resourceBundle.getString("second_notification"));
        breakOnButton.setText(resourceBundle.getString("break_on"));
        breakOffButton.setText(resourceBundle.getString("break_off"));
        editButton.setText(resourceBundle.getString("employee_edit_button"));
    }


    public void showGoogleBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("http://www.google.com"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void showAntiStormBrowser(){
        try {
            Desktop.getDesktop().browse(new URI("http://antistorm.eu/"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void showPhoneView() throws IOException {

        AnchorPane phoneView = FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/phone/phoneView.fxml"));
        phoneStage = new Stage();
        phoneStage.setTitle("Phone");
        phoneStage.setResizable(false);
        phoneStage.getIcons().add(new Image("images/phone.png",25,20,false,false));
        phoneStage.setScene(new Scene(phoneView));
        phoneStage.setOnCloseRequest(e -> {
            e.consume();
            phoneStage.close();
        });
        phoneStage.show();
        commandMediator.registerDistributorPhoneService(new DistributorPhoneService(commandMediator));
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
    public void showEmployeeEditView(ActionEvent event) throws IOException, InterruptedException {
        commandMediator.registerDistributorEmployeeEditService(new DistributorEmployeeEditService(commandMediator));
        Employee employee = commandMediator.getEmployeeToEdit();
        commandMediator.sendForEmployeeDataToEdit(employee);
        employeeEditViews = FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/employeeData/distributorPanelEditingEmployeeView.fxml"),resourceBundle);
        Stage employeeEditStage = new Stage();
        employeeEditStage.setTitle("Edit data");
        employeeEditStage.setScene(new Scene(employeeEditViews));
        employeeEditStage.setOnCloseRequest(e -> {
            e.consume();
        });
        employeeEditStage.show();
    }

    @FXML
    public void manageDistributorBreak(ActionEvent event){
        ToggleButton source = (ToggleButton) event.getSource();
        String id = source.getId();
        if(id.equals("breakOnButton")){
            commandMediator.breakForUser(true,tableWithAllSystemNotifications,tableWithUserNotifications,googleMapPane);
        }
        else if (id.equals("breakOffButton")) {
            commandMediator.breakForUser(false,tableWithAllSystemNotifications,tableWithUserNotifications,googleMapPane);
        }
    }
}



