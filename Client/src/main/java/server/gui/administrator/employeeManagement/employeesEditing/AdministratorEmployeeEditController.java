package server.gui.administrator.employeeManagement.employeesEditing;

import javafx.concurrent.Task;
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
import server.model.employee.Employee;
import server.model.employee.EmployeeProfileType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdministratorEmployeeEditController implements Initializable {

    @FXML
    private Button editButton = new Button();

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

    private Employee originalEmployee;

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
    public void editEmployee(ActionEvent e){

        emailTextField.setStyle(null);
        firstPasswordField.setStyle(null);
        secondPasswordField.setStyle(null);
        emailValidateLabel.setVisible(false);
        if(firstNameTextField.getText()!=null
                && lastNameTextField.getText() != null
                && emailTextField.getText() != null
                && firstPasswordField.getText() != null
                && loginTextField.getText().equals("") != true
                && firstPasswordField.getText() != null) {
            if(validateEmail(emailTextField.getText()) == true) {
                if (((firstPasswordField.getText()).equals(secondPasswordField.getText())==true ) && (firstPasswordField.getText() != null)) {
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String type = employeeTypeChoiceBox.getValue();
                    String login = loginTextField.getText();
                    String password = secondPasswordField.getText();
                    commandMediator.sendDataToEditEmployee(firstName, lastName, email, type, password, login, imageView, originalEmployee);
                    Stage stage = (Stage) editButton.getScene().getWindow();
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
    public void closeEditEmployeeWindow(ActionEvent e){
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
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());
            emailTextField.setText(employee.getEmail());
            if(employee.getEmployeeImage()!= null && employee.getEmployeeImage().getImage() != null){
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(employee.getEmployeeImage().getImage());
                try {
                    BufferedImage  bufferedImage = ImageIO.read(byteArrayInputStream);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(image);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (employee.getType() == EmployeeProfileType.ADMINISTRATOR)
                employeeTypeChoiceBox.getSelectionModel().select(0);
            else
                employeeTypeChoiceBox.getSelectionModel().select(1);
            loginTextField.setText(employee.getEmployeeAccount().getLogin());
            firstPasswordField.setText(employee.getEmployeeAccount().getPassword());
            secondPasswordField.setText(employee.getEmployeeAccount().getPassword());
            try {
                originalEmployee = (Employee) employee.clone();
            } catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
            }

        });

         new Thread(employeeEditTask).start();

    }
}
