package server.gui.distributor.builder;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.notification.AccidentType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Class which is uses into javaFX (in java table) in view for institution
 */
public class NotificationForTable {


    public static class NotificationBuilder {


        private final SimpleStringProperty accidentType = new SimpleStringProperty("");
        private final SimpleStringProperty timeCreated = new  SimpleStringProperty("");
        private  SimpleStringProperty institutionType = new SimpleStringProperty("");
        private  SimpleStringProperty distributorData = new SimpleStringProperty("");
        private ImageView status = null;
        private SimpleStringProperty callerData = new SimpleStringProperty("");
        private SimpleStringProperty callerPhoneNumber = new SimpleStringProperty("");
        private SimpleStringProperty city = new SimpleStringProperty("");

        public NotificationBuilder(AccidentType accidentType, Date timeCreated){
            if(accidentType == AccidentType.NAPAD)
                this.accidentType.set("napad");
            else if (accidentType == AccidentType.POŻAR)
                this.accidentType.set("pozar");
            else if (accidentType == AccidentType.WYBUCH)
                this.accidentType.set("wybuch");
            else
                this.accidentType.set("inne");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
            this.timeCreated.set(sf.format(timeCreated));
        }

        public NotificationBuilder institutionType(List<Institution> institution){
            String institutionToTmp = "";
            for (Institution oneInstitution :institution) {
                if(oneInstitution.getInstitutionType() == InstitutionType.FIRE_BRIGADE)
                    institutionToTmp = institutionToTmp + "fire brigade, ";
                else if (oneInstitution.getInstitutionType() == InstitutionType.POLICE)
                    institutionToTmp = institutionToTmp + "police, ";
                else if(oneInstitution.getInstitutionType() == InstitutionType.EMERGENCY)
                    institutionToTmp = institutionToTmp + "emergency, ";
                else
                    institutionToTmp =  null;
            }
            this.institutionType.set(institutionToTmp);
            return this;
        }

        public NotificationBuilder distributorData(String firstName, String lastName) {
            this.distributorData.set(firstName + " " + lastName);
            return this;
        }

        public NotificationBuilder status(int number){
            Image statusImage = null;
            if(number == 1){
                statusImage = new Image("images/status/yellow-status.png",10,10,false,false);
            }else if (number == 2){
                statusImage = new Image("images/status/green-status.png",10,10,false,false);
            } else if (number == 3){
                statusImage = new Image("images/status/red-status.png",10,10,false,false);
            }
            ImageView statusImageView = new ImageView(statusImage);
            this.status = statusImageView;
            return this;
        }

        public NotificationBuilder callerData(String firstName, String lastName){
            this.callerData.set(firstName + " " + lastName);
            return this;
        }

        public NotificationBuilder callerNumber(String number){
            this.callerPhoneNumber.set(number);
            return this;
        }

        public NotificationBuilder city(String city){
            this.city.set(city);
            return this;
        }

        public NotificationForDistributorTables build(){
            return new NotificationForDistributorTables(accidentType,
                    timeCreated,institutionType,distributorData,status,callerData,
                    callerPhoneNumber,city);
        }
    }
}
