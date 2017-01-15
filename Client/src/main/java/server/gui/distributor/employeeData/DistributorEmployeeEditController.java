package server.gui.distributor.employeeData;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistributorEmployeeEditController implements Initializable{

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstLoginTextField;

    @FXML
    private TextField secondLoginTextField;

    @FXML
    private PasswordField firstPasswordField;

    @FXML
    private PasswordField secondPasswordField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button loadImageButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    private Employee originalEmployee;

    private DistributorCommandMediator commandMediator;

    private Pattern pattern =Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean validateEmail(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        commandMediator = DistributorCommandMediator.getInstance();


        loadImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Add Image");
            fileChooser.setInitialDirectory(new File
                    (System.getProperty("user.home")));
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(image);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Task<Employee> employeeEditTask = new Task<Employee>(){

            @Override
            protected Employee call() throws Exception {
                Employee employee = null;
                do {
                    Thread.sleep(1000);
                    employee = commandMediator.giveEmployeeFromServer();

                }while (employee == null);
                return  employee;
            }
        };


        employeeEditTask.setOnSucceeded(e-> {
            Employee employee = employeeEditTask.getValue();
            try {
                originalEmployee = (Employee) employee.clone();
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());
            emailTextField.setText(employee.getEmail());
            if(employee.getEmployeeImage()!= null && employee.getEmployeeImage().getImage() != null){
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(employee.getEmployeeImage().getImage());
                try {
                    BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(image);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }else{
                Image nullEmployeeImage = new Image("images/employee-image.png",75,75,false,false);
                imageView.setImage(nullEmployeeImage);
            }
            firstLoginTextField.setText(employee.getEmployeeAccount().getLogin());
            secondLoginTextField.setPromptText("repeat login");
            firstPasswordField.setText(employee.getEmployeeAccount().getPassword());
            secondPasswordField.setPromptText("repeat password");

        });

        new Thread(employeeEditTask).start();
    }

    @FXML
    public void closeEditEmployeeWindow(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }


    public void showDifferentPasswordPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("Editing passwords is different");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showEmptyFieldPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("You leave empty field ");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void editYourEmployeeData(ActionEvent e) {
        emailTextField.setStyle(null);
        firstPasswordField.setStyle(null);
        secondPasswordField.setStyle(null);
        emailTextField.setStyle(null);
        if(firstNameTextField.getText()!=null
                && lastNameTextField.getText() != null
                && emailTextField.getText() != null
                && firstLoginTextField.getText().equals("") != true
                && secondLoginTextField.getText().equals("") != true
                && firstPasswordField.getText() != null
                && secondPasswordField.getText() != null) {
            if(validateEmail(emailTextField.getText()) == true) {
                if (((firstPasswordField.getText()).equals(secondPasswordField.getText())==true ) && (firstPasswordField.getText() != null)) {
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String login = secondLoginTextField.getText();
                    String password = secondPasswordField.getText();
                    commandMediator.sendDataToEditEmployee(firstName, lastName, email, password, login, imageView, originalEmployee);
                    Stage stage = (Stage) editButton.getScene().getWindow();
                    stage.close();
                } else {
                    showDifferentPasswordPopup();
                    firstPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                    secondPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                }
            }else{
                emailTextField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                emailTextField.setVisible(true);
            }

        } else {
            showEmptyFieldPopup();
        }
    }
}
