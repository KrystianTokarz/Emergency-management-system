package server.gui.administrator.employeeManagement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

/**
 * Class which is uses into javaFX (in java table) in view for employee
 */
public class EmployeeForTable {

    private ImageView image = null;

    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName  = new SimpleStringProperty("");;
    private SimpleStringProperty email  = new SimpleStringProperty("");;
    private SimpleStringProperty type  = new SimpleStringProperty("");;
    private SimpleBooleanProperty availability  = new SimpleBooleanProperty();

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        image.setFitHeight(50);
        image.setFitWidth(150);
        image.setPreserveRatio(true);
        this.image = image;
    }

    public String getFirstName() {
       return this.firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return  this.lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public boolean isAvailability() {
        return  availability.get();
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }
}
