package server.gui.distributor.receivingPanel;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.gui.distributor.notificationPanel.DistributorNotificationService;
import server.message.mediator.DistributorCommandMediator;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DistributorReceivingController implements Initializable {

    @FXML
    private Button acceptButton;

    @FXML
    private TableView callerTableView;

    @FXML
    private Label distributorLabel;

    @FXML
    private Button repeatButton;


    private  ObservableList<CallerForTable> observableCallerForTableList;

    private int numberTimelineEvent;

    private Timeline timelineCallForDistributor;

    private DistributorCommandMediator distributorCommandMediator;

    private Stage notificationStage = null;

    private ResourceBundle resourceBundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        loadInternationalizationNames();

        Image acceptImage = new Image("images/visibility.png",50,50,false,false);
        acceptButton.setGraphic(new ImageView(acceptImage));


        observableCallerForTableList = FXCollections.observableArrayList();
        distributorCommandMediator = DistributorCommandMediator.getInstance();
        timelineCallForDistributor = distributorCommandMediator.giveTimelineForNotificationCaller();

        timelineCallForDistributor.getKeyFrames().add(
                new KeyFrame(Duration.seconds(10),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                distributorCommandMediator.setObservableCallerForTableList(observableCallerForTableList);
                                observableCallerForTableList.add(new CallerForTable(CallerDataMock.getNumber(numberTimelineEvent),CallerDataMock.getLocation(numberTimelineEvent)));
                                callerTableView.setItems(observableCallerForTableList);
                                numberTimelineEvent = (numberTimelineEvent+ 1)% 10;
                            }
                        }
                )
        );
        timelineCallForDistributor.setCycleCount(Animation.INDEFINITE);
        timelineCallForDistributor.play();
    }
    public void loadInternationalizationNames(){
        distributorLabel.setText(resourceBundle.getString("addition_panel_name"));
        repeatButton.setText(resourceBundle.getString("repeat_button"));

    }


    @FXML
    public void acceptNotification() throws IOException {
        try{
            CallerForTable callerForTable = observableCallerForTableList.get(0);
            distributorCommandMediator.registerDistributorNotificationService(new DistributorNotificationService(distributorCommandMediator,callerForTable,0));
            distributorCommandMediator.sendMessageForLocalizationForNotification();
            observableCallerForTableList.remove(callerForTable);
            callerTableView.refresh();
            notificationStage = new Stage();
            AnchorPane notificationView = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/notification/distributorPanelNotificationsView.fxml"),resourceBundle);
            notificationStage.setScene(new Scene(notificationView));
            notificationStage.setTitle("NOTIFICATION");
            notificationStage.show();

        }catch (IndexOutOfBoundsException ex){
            callerTableView.refresh();
        }
    }

    @FXML
    public void createNotification() throws IOException {
            CallerForTable callerForTable = null;
            distributorCommandMediator.registerDistributorNotificationService(new DistributorNotificationService(distributorCommandMediator,callerForTable,1));
            distributorCommandMediator.sendMessageForLocalizationForNotification();
            notificationStage = new Stage();
            AnchorPane notificationView = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("views/distributor/notification/distributorPanelNotificationsView.fxml"),resourceBundle);
            notificationStage.setScene(new Scene(notificationView));

            notificationStage.setTitle("NOTIFICATION");
            notificationStage.show();


    }
}
