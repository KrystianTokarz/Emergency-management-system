package server.gui.administrator.institutionManagement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which is uses into javaFX (in java table) in view for institution
 */
public class InstitutionForTable {

    private ImageView image = null;
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty type  = new SimpleStringProperty("");
    private SimpleBooleanProperty availability = new SimpleBooleanProperty();

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        image.setFitHeight(50);
        image.setFitWidth(150);
        image.setPreserveRatio(true);
        this.image = image;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public boolean isAvailability() {
        return availability.get();
    }

    public SimpleBooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }



}
