package server.gui.administrator.employeeManagement.employeesAdding;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.message.mediator.CommandMediator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdministratorEmployeeAddController implements Initializable {

    @FXML
    private Button saveButton = new Button();

    @FXML
    private Button cancelButton = new Button();

    @FXML
    private Button loadImageButton = new Button();

    @FXML
    private TextField firstNameTextField = new TextField();

    @FXML
    private TextField  lastNameTextField= new TextField();

    @FXML
    private TextField  emailTextField= new TextField();

    @FXML
    private ChoiceBox<String> employeeTypeChoiceBox = new ChoiceBox<>();

    @FXML
    private TextField  loginTextField= new TextField();

    @FXML
    private PasswordField firstPasswordField = new PasswordField();

    @FXML
    private PasswordField secondPasswordField = new PasswordField();

    @FXML
    private ImageView imageView = new ImageView();

    @FXML
    private Label emailValidateLabel;

    private CommandMediator commandMediator = CommandMediator.getInstance();

    private Pattern pattern =Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean validateEmail(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    public void showDifferentPasswordPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("Passwords is different");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showEmptyFieldPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("Your leave empty field");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void saveEmployee(ActionEvent e){

        emailTextField.setStyle(null);
        firstPasswordField.setStyle(null);
        secondPasswordField.setStyle(null);
        emailValidateLabel.setVisible(false);
        if(firstNameTextField.getText().equals("") != true
                && lastNameTextField.getText().equals("") != true
                && emailTextField.getText().equals("") != true
                && loginTextField.getText().equals("") != true
                && firstPasswordField.getText().equals("") != true
                && firstPasswordField.getText().equals("") != true) {
            if(validateEmail(emailTextField.getText()) == true) {
                if (((firstPasswordField.getText()).equals(secondPasswordField.getText()) == true) && (firstPasswordField.getText().equals("") != true)) {
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String type = employeeTypeChoiceBox.getValue();
                    String login = loginTextField.getText();
                    String password = secondPasswordField.getText();
                    commandMediator.sendDataForEmployee(firstName, lastName, email, type, password, login, imageView);
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();

                } else {
                    showDifferentPasswordPopup();
                    firstPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                    secondPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");

                }
            }else{
                emailTextField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                emailValidateLabel.setVisible(true);
            }
        } else {
            showEmptyFieldPopup();
        }
    }


    @FXML
    public void closeNewEmployeeWindow(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        loadImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add Image");
            fileChooser.setInitialDirectory(new File
                    (System.getProperty("user.home")));
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            File file = fileChooser.showOpenDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage,null);
                imageView.setImage(image);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}
