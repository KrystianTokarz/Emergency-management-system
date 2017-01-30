package server.gui.administrator.institutionManagement.institutionState;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import server.gui.administrator.institutionManagement.InstitutionForTable;
import server.message.mediator.CommandMediator;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.ProvinceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorInstitutionOperationController implements Initializable {

    @FXML
    private Label nameOfOperationLabel = new Label();
    @FXML
    private Button operationButton = new Button();

    @FXML
    private Button cancelButton = new Button();

    @FXML
    private Button loadImageButton = new Button();

    @FXML
    private TextField institutionNameTextField = new TextField();

    @FXML
    private ChoiceBox<String> institutionTypeChoiceBox = new ChoiceBox<>();

    @FXML
    private ImageView imageView = new ImageView();

    @FXML
    private RadioButton yesRadioButton = new RadioButton();

    @FXML
    private RadioButton noRadioButton = new RadioButton();

    @FXML
    private ChoiceBox<String> provinceTypeChoiceBox = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> localityTypeChoiceBox = new ChoiceBox<>();

    @FXML
    private TextField streetTextField = new TextField();

    @FXML
    private TextField numberNameTextField = new TextField();


    private CommandMediator commandMediator = CommandMediator.getInstance();

    private Institution originalInstitution;




    public void showEmptyFieldPopup(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setContentText("You leave empty field ");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        commandMediator.setAllFXMLElements(operationButton, nameOfOperationLabel);
        commandMediator.setViewForSelectedView();
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

        ObservableList<String> nameOfLocality = FXCollections.observableArrayList();
        localityTypeChoiceBox.setDisable(true);

        provinceTypeChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {

            if ((oldValue != null) && (oldValue.equals(newValue)))
                return;
            else {
                localityTypeChoiceBox.setDisable(false);
                List<Institution> rightInstitutionList = null;
                localityTypeChoiceBox.getItems().clear();
                    if (newValue.equals("Swietokrzyskie")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.SWIETOKRZYSKIE);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }
                        }
                    }
                    else if (newValue.equals("Lodzkie")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.LODZKIE);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }
                        }
                    }
                    else if (newValue.equals("Maslovia")) {
                        rightInstitutionList = commandMediator.getRightInstitutionList(ProvinceType.MASLOVIA);
                        if(rightInstitutionList.size()!=0) {
                            List<Locality> localityList = rightInstitutionList.get(0).getProvince().getLocalityList();
                            for (Locality locality : localityList) {
                                nameOfLocality.add(locality.getLocality());
                            }
                        }
                    }
                localityTypeChoiceBox.setItems(nameOfLocality);
            }
        });

        if(commandMediator.getValueSelectedView() == 2) {
            Task<Institution> institutionEditTask = new Task<Institution>() {

                @Override
                protected Institution call() throws Exception {
                    Institution institution = null;
                    do {
                        Thread.sleep(1000);
                        institution = commandMediator.giveInstitutionFromServer();
                    } while (institution == null);
                    return institution;
                }
            };


            institutionEditTask.setOnSucceeded(e -> {
                Institution institution = institutionEditTask.getValue();
                institutionNameTextField.setText(institution.getName());
                if (institution.getProvince().getProvinceType() == ProvinceType.SWIETOKRZYSKIE) {
                    provinceTypeChoiceBox.getSelectionModel().select(0);
                } else if (institution.getProvince().getProvinceType() == ProvinceType.LODZKIE) {
                    provinceTypeChoiceBox.getSelectionModel().select(1);
                } else if (institution.getProvince().getProvinceType() == ProvinceType.MASLOVIA) {
                    provinceTypeChoiceBox.getSelectionModel().select(2);
                }

                String realLocality = institution.getLocality().getLocality();
                localityTypeChoiceBox.getSelectionModel().select(realLocality);
                streetTextField.setText(institution.getStreet().getStreet());
                numberNameTextField.setText(institution.getStreet().getSpecialNumber());
                if (institution.getAvailability() == true)
                    yesRadioButton.setSelected(true);
                else
                    noRadioButton.setSelected(true);
                if (institution.getInstitutionImage() != null && institution.getInstitutionImage().getImage() != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(institution.getInstitutionImage().getImage());
                    try {
                        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        imageView.setImage(image);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                if (institution.getInstitutionType() == InstitutionType.EMERGENCY)
                    institutionTypeChoiceBox.getSelectionModel().select(0);
                else if (institution.getInstitutionType() == InstitutionType.FIRE_BRIGADE)
                    institutionTypeChoiceBox.getSelectionModel().select(1);
                else if (institution.getInstitutionType() == InstitutionType.POLICE)
                    institutionTypeChoiceBox.getSelectionModel().select(2);

                try {
                    originalInstitution = (Institution) institution.clone();
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            });

            new Thread(institutionEditTask).start();
        }
    }

    @FXML
    public void closeEditInstitutionWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void operationForInstitution(){
        boolean selectedValue = true;
        if(institutionNameTextField.getText().equals("") != true
                && institutionTypeChoiceBox.getSelectionModel().getSelectedIndex() != -1
                && provinceTypeChoiceBox.getSelectionModel().getSelectedIndex() != -1
                && localityTypeChoiceBox.getSelectionModel().getSelectedIndex() != -1
                && streetTextField.getText().equals("") != true
                && numberNameTextField.getText().equals("") != true){
            String institutionName = institutionNameTextField.getText();
            String institutionType = institutionTypeChoiceBox.getSelectionModel().getSelectedItem();
            String provinceType = provinceTypeChoiceBox.getSelectionModel().getSelectedItem();
            String localityType = localityTypeChoiceBox.getSelectionModel().getSelectedItem();
            String streetName = streetTextField.getText();
            String numberName = numberNameTextField.getText();
            if(yesRadioButton.isSelected())
                selectedValue = true;
            else
                selectedValue = false;
            commandMediator.sendDataForSelectedViewInstitution(institutionName, institutionType, provinceType, localityType, streetName, numberName,imageView,selectedValue, originalInstitution);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();


        } else {
            showEmptyFieldPopup();
        }
    }
}
