package server.gui.distributor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.util.Duration;
import server.communication.DataStream;
import server.communication.EchoThread;
import server.gui.distributor.factory.NotificationForDistributorTables;
import server.gui.distributor.observerReceivingPanel.ObservableConcrete;
import server.gui.distributor.receivingPanel.CallerForTable;
import server.message.Message;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;
import server.model.message.MessageType;
import server.model.notification.Notification;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistributorService {

    private Message message;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] image;
    private Timeline locationUpdateTimeline;
    private Timeline timelineForAllNotification = new Timeline();
    private Timeline timelineForUserNotification = new Timeline();
    private List<Notification> notificationForDistributorList = null;
    private List<Notification> notificationListForApplication = null;

    private ObservableConcrete additionalPanelObserver;

    private DistributorCommandMediator commandMediator;
    private ObservableList<CallerForTable> observableCallerForTableList;


    public DistributorService(DistributorCommandMediator commandMediator, ObservableConcrete observableConcrete){
        this.commandMediator = commandMediator;
        this.additionalPanelObserver = observableConcrete;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String loadEmployeeFirstAndLastName(){
        return firstName + "  " + lastName;
    }

    public byte[] loadEmployeeImageView(){
        return image;
    }


    public void logout(){
        DataStream dataStream = DataStream.getInstance();
        try {
            dataStream.getSocket().shutdownInput();
            dataStream.getSocket().shutdownOutput();
            dataStream.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BorderPane createGoogleMap(WebEngine webEngine) {
        final ToggleGroup mapTypeGroup = new ToggleGroup();
        final ToggleButton road = new ToggleButton("Road");
        road.setSelected(true);
        road.setToggleGroup(mapTypeGroup);
        final ToggleButton satellite = new ToggleButton("Satellite");
        satellite.setToggleGroup(mapTypeGroup);
        final ToggleButton hybrid = new ToggleButton("Hybrid");
        hybrid.setToggleGroup(mapTypeGroup);
        final ToggleButton terrain = new ToggleButton("Terrain");
        terrain.setToggleGroup(mapTypeGroup);
        mapTypeGroup.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(
                            ObservableValue<? extends Toggle> observableValue,
                            Toggle toggle, Toggle toggle1) {
                        if (road.isSelected()) {
                            webEngine.executeScript("document.setMapTypeRoad()");
                        } else if (satellite.isSelected()) {
                            webEngine.executeScript("document.setMapTypeSatellite()");
                        } else if (hybrid.isSelected()) {
                            webEngine.executeScript("document.setMapTypeHybrid()");
                        }else if (terrain.isSelected()) {
                            webEngine.executeScript("document.setMapTypeTerrain()");}
                    }
                });

        final TextField searchBox = new TextField("Kielce");
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue,
                    String s, String s1) {
                if (locationUpdateTimeline!=null) locationUpdateTimeline.stop();
                locationUpdateTimeline = new Timeline();
                locationUpdateTimeline.getKeyFrames().add(
                        new KeyFrame(new Duration(400),
                                new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent actionEvent) {
                                        webEngine.executeScript("document.goToLocation(\""+
                                                searchBox.getText()+"\")");
                                    }
                                })
                );
                locationUpdateTimeline.play();
            }
        });
        Button zoomIn = new Button(" + ");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomIn()");
            }
        });
        Button zoomOut = new Button(" - ");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomOut()");
            }
        });

        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                createSpacer(),
                new Label("Location:"),searchBox, zoomIn, zoomOut);
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setTop(toolBar);
        return root;
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    static {
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public Employee getEmployeeToEdit() {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        return employee;
    }

    public void sendForEmployeeDataToEdit(Employee employee) {
        Message message = new Message.MessageBuilder(MessageType.SEND_EMPLOYEE_DISTRIBUTOR)
                .object(employee)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void activeEmergencyAlarm() {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

        Mixer mixer = AudioSystem.getMixer(mixerInfo[0]);

        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        Clip clip = null;
        try {
             clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        URL soundUrl = getClass().getClassLoader().getResource("alert/alarm.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.start();
    }


    public Timeline give4SecondTimelineForNotificationInApplication() {
        return timelineForAllNotification;
    }

    public Timeline give4SecondTimelineForNotificationForUser() {
        return timelineForUserNotification;
    }

//    public void sendMessageFromUserNotification() {
//        Employee employee = new Employee();
//        employee.setFirstName(firstName);
//        employee.setLastName(lastName);
//        employee.setEmail(email);
//        Message message = new Message.MessageBuilder(MessageType.SEND_NOTIFICATION_FOR_DISTRIBUTOR)
//                .object(employee)
//                .build();
//        Thread thread = new EchoThread(message);
//        thread.start();
//
//    }

    public void sendMessageForAllNotification(){
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        Message message = new Message.MessageBuilder(MessageType.SEND_NOTIFICATION)
                .object(employee)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();

    }

//    public void setNotificationListForDistributor(Object notificationListForDistributor) {
//        this.notificationForDistributorList = (List<Notification>) notificationListForDistributor;
//    }
//
    public List<Notification> getNotificationListForDistributor() {
        return this.notificationForDistributorList;
    }

    public void setNotificationListForApplication(Object notificationListForApplication) {
        this.notificationForDistributorList = (List<Notification>) ((Map<String,List<Notification>>) notificationListForApplication).get("forUser");
        this.notificationListForApplication = (List<Notification>) ((Map<String,List<Notification>>) notificationListForApplication).get("forAll");
    }

    public List<Notification> getNotificationForAllApplicationTable() {
        return notificationListForApplication;
    }

    public void alarmAllObserver(boolean b) {
        additionalPanelObserver.setState(b);
    }

    public void setObservableCallerForTableList(ObservableList<CallerForTable> observableCallerForTableList) {
        this.observableCallerForTableList = observableCallerForTableList;
    }

    private void  setBreakView (boolean b, TableView tableWithAllSystemNotifications, TableView tableWithUserNotifications, Pane googleMapPane) {
        tableWithAllSystemNotifications.setDisable(b);
        tableWithUserNotifications.setDisable(b);
        googleMapPane.setDisable(b);
        commandMediator.alarmAllObserver(b);
    }

    public void breakForUser(boolean b, TableView tableWithAllSystemNotifications, TableView tableWithUserNotifications, Pane googleMapPane) {
        if(b==true && (observableCallerForTableList == null || observableCallerForTableList.size()==0)) {
           setBreakView(b,tableWithAllSystemNotifications, tableWithUserNotifications,googleMapPane);
        }
        else if(b==false){
            setBreakView(b,tableWithAllSystemNotifications, tableWithUserNotifications,googleMapPane);
        }
    }






    public void acceptableMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Your notification is completed !");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    public void nonAcceptableMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Your notification is not completed !");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    public void startThread(){
        Task task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Thread.sleep(17000);
                boolean result = commandMediator.returnResultOfSaveAllNotificationInDatabase();
                int size = notificationForDistributorList.size();
                Notification notification = notificationForDistributorList.get(size - 1);
                boolean status = false;
                if(notification.getStatus() ==2)
                    status = true;

                if( result == true && status == true ){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            acceptableMessage();
                        }
                    });
                }else if (result == false || status == false){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            nonAcceptableMessage();
                        }
                    });
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        //thread.setDaemon(true);
        thread.start();
    }
}

class StringUtilities {
    /**
     * Creates a string left padded to the specified width with the supplied padding character.
     * @param fieldWidth the length of the resultant padded string.
     * @param padChar a character to use for padding the string.
     * @param s the string to be padded.
     * @return the padded string.
     */
    public static String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }
}

