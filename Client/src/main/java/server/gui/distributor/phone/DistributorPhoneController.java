package server.gui.distributor.phone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import server.message.mediator.DistributorCommandMediator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class DistributorPhoneController implements Initializable {

    @FXML
    private Button cancelCallButton;

    @FXML
    private Button callButton;

    @FXML
    private TextField numberPhoneTextField;

    @FXML
    private Button backOneStepButton;

    private Image cancelCallImage;

    private DistributorCommandMediator commandMediator = DistributorCommandMediator.getInstance();


    @FXML
    public void clickNumberButton(ActionEvent event) {
        Button source = (Button) event.getSource();
        String id = source.getId();
        char number = id.charAt(6);
        String oldText = numberPhoneTextField.getText();
        String newText = oldText + number;
        numberPhoneTextField.setText(newText);

    }
    @FXML
    public void backOneStepButton(){
        String oldText = numberPhoneTextField.getText();
        if(oldText.length()>0) {
            String newText = oldText.substring(0, oldText.length() - 1);
            numberPhoneTextField.setText(newText);
        }
    }



        @Override
    public void initialize(URL location, ResourceBundle resources) {


        Image callImage = new Image("images/green-call.png",25,20,false,false);
        cancelCallImage = new Image("images/red-call.png",25,20,false,false);
        Image backArrowImage = new Image("images/back-arrow.png",25,20,false,false);
        callButton.setGraphic(new ImageView(callImage));
        cancelCallButton.setGraphic(new ImageView(cancelCallImage));
        backOneStepButton.setGraphic(new ImageView(backArrowImage));


    }

    public void closePhoneView() {
        Stage stage = (Stage) cancelCallButton.getScene().getWindow();
        stage.close();
    }
    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{9}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) return true;
        else return false;

    }


    public void makeCall(){

        String phoneNumber = numberPhoneTextField.getText();
        System.out.println(phoneNumber + "phoneNumber");
        if (phoneNumber!= null && validatePhoneNumber(phoneNumber)) {
            Stage stage = (Stage) callButton.getScene().getWindow();
            VBox root = new VBox();
            root.setSpacing(20);
            root.setPadding(new Insets(20,50,100,50));
            root.setAlignment(Pos.TOP_CENTER);
            Label label1 = new Label();
            label1.setFont(Font.font("Verdana",FontWeight.BOLD,16));
            label1.setText("  " + phoneNumber + "  ");


            Button button = new Button();
            button.setPrefSize(130,70);
            button.setGraphic(new ImageView(cancelCallImage));
            button.setOnAction(event -> {
                stage.close();
            });

            ProgressBar p2 = new ProgressBar();
            root.getChildren().addAll(p2,label1,button);
            Scene scene = new Scene(root, 300, 180);
            stage.setScene(scene);
            stage.show();
            commandMediator.activePhoneRinging();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The number entered is incorrect!");
            alert.showAndWait();
        }

        }


}
