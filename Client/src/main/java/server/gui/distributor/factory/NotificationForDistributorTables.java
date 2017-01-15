package server.gui.distributor.factory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.notification.AccidentType;
import java.util.Date;
import java.util.List;

public class NotificationForDistributorTables {

    private SimpleStringProperty accidentType = new SimpleStringProperty("");
    private SimpleStringProperty timeCreated = new SimpleStringProperty("");
    private SimpleStringProperty institutionType = new SimpleStringProperty("");
    private SimpleStringProperty distributorData = new SimpleStringProperty("");
    private ImageView status = null;
    private SimpleStringProperty callerData = new SimpleStringProperty("");
    private SimpleStringProperty callerPhoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty city = new SimpleStringProperty("");

    public NotificationForDistributorTables(SimpleStringProperty accidentType, SimpleStringProperty timeCreated,
                                            SimpleStringProperty institutionType, SimpleStringProperty distributorData,
                                            ImageView status,
                                            SimpleStringProperty callerData,
                                            SimpleStringProperty callerPhoneNumber,
                                            SimpleStringProperty city) {

        this.accidentType = accidentType;
        this.timeCreated = timeCreated;
        this.institutionType = institutionType;
        this.distributorData = distributorData;
        this.status = status;
        this.callerData = callerData;
        this.callerPhoneNumber = callerPhoneNumber;
        this.city = city;
    }

    public String getAccidentType() {
        return accidentType.get();
    }

    public SimpleStringProperty accidentTypeProperty() {
        return accidentType;
    }

    public String getTimeCreated() {
        return timeCreated.get();
    }

    public SimpleStringProperty timeCreatedProperty() {
        return timeCreated;
    }

    public String getInstitutionType() {
        return institutionType.get();
    }

    public SimpleStringProperty institutionTypeProperty() {
        return institutionType;
    }

    public String getDistributorData() {
        return distributorData.get();
    }

    public SimpleStringProperty distributorDataProperty() {
        return distributorData;
    }

    public ImageView getStatus() {
        return status;
    }


    public String getCallerData() {
        return callerData.get();
    }

    public SimpleStringProperty callerDataProperty() {
        return callerData;
    }

    public String getCallerPhoneNumber() {
        return callerPhoneNumber.get();
    }

    public SimpleStringProperty callerPhoneNumberProperty() {
        return callerPhoneNumber;
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }
}
