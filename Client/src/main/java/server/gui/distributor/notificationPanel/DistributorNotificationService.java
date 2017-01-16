package server.gui.distributor.notificationPanel;

import javafx.scene.control.ChoiceBox;
import server.communication.EchoThread;
import server.gui.distributor.receivingPanel.CallerForTable;
import server.message.Message;
import server.message.mediator.DistributorCommandMediator;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.Street;
import server.model.message.MessageType;
import server.model.message.MessageWithNotification;
import server.model.message.SecondMessageWithNotification;

import java.util.ArrayList;
import java.util.List;

public class DistributorNotificationService {

    private DistributorCommandMediator commandMediator;
    private CallerForTable callerForTable;
    private List<Province> localizationForServer;
    private List<Institution> institutionForServer;
    private ChoiceBox policeChoiceBox;
    private ChoiceBox emergencyChoiceBox;
    private ChoiceBox fireBrigadeChoiceBox;
    private String policeNameInstitution;
    private String emergencyNameInstitution;
    private String fireBrigadeNameInstitution;
    private Long notificationId;
    private Boolean resultNotificationInServer = false;

    public DistributorNotificationService(DistributorCommandMediator distributorCommandMediator, CallerForTable callerForTable){
        this.commandMediator = distributorCommandMediator;
        this.callerForTable = callerForTable;
    }

    public CallerForTable giveDataCaller(){
        return this.callerForTable;
    }

    public void setLocalizationForServer(List<Province> localizationForServer) {
        this.localizationForServer = localizationForServer;
    }

    public List<String> returnAllProvince(){
        System.out.println("..." + localizationForServer);
        List<String> provinceNameList = new ArrayList<>();
        for (Province province: localizationForServer) {
            provinceNameList.add(province.getProvinceType().toString());
        }
        return provinceNameList;
    }

    public String getProvinceForLocality(String localityString){
        Province tmpProvince = null;

        petlaOstatnia :for (Province province: localizationForServer) {
            List<Locality> localityList = province.getLocalityList();
            for (Locality localityInList: localityList) {
                if(localityInList.getLocality().equals(localityString)) {
                    tmpProvince = province;
                    break petlaOstatnia;
                }
            }
        }
        return tmpProvince.getProvinceType().toString();
    }

    public List<Locality> getLocalityForSelectedProvince(String provinceName){
        Province tmpProvince = null;
        for (Province province: localizationForServer) {
            if(province.getProvinceType().toString().equals(provinceName)) {
                tmpProvince = province;
                break;
            }
        }
        List<Locality> localityList = tmpProvince.getLocalityList();
        //List<String> localityNameList = new ArrayList<>();
//        for (Locality locality: localityList) {
//            localityNameList.add(locality.getLocality());
//        }
        return localityList;
    }

    public List<Street> getStreetForSelectedLocality(String provinceName, String localityName){
        List<Locality> localityForSelectedProvince = getLocalityForSelectedProvince(provinceName);
        Locality selectedLocality = null;
        for(Locality locality: localityForSelectedProvince){
            if(locality.getLocality().equals(localityName)) {
                selectedLocality = locality;
            }
        }

        return selectedLocality.getStreetList();


    }


    public void sendForInstitutionDataToEdit() {
        Message message = new Message.MessageBuilder(MessageType.SEND_FOR_INSTITUTION_FOR_LOCALIZATION)
                .object(null)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void setInstitutionForServer(List<Institution> institutionForServer) {
        this.institutionForServer = institutionForServer;
    }


    public List<Institution> getRightInstitutionListForProvinceAndLocality(InstitutionType type, String locality){
        if (type == null){
            return institutionForServer;
        }
        List<Institution> rightInstitutionListForLocality = getRightInstitutionListForLocality(locality, institutionForServer);
        List<Institution> rightInstitution = new ArrayList<>();
        for (Institution institution: rightInstitutionListForLocality) {
            if(institution.getInstitutionType() == type){
                rightInstitution.add(institution);
            }
        }
        return rightInstitution;
    }

    public List<Institution> getRightInstitutionListForLocality(String locality, List<Institution> allInstitutionList){
        List<Institution> rightInstitution = new ArrayList<>();
        for (Institution institution: allInstitutionList) {
                Locality tmpLocality = institution.getLocality();
                {
                    if (tmpLocality.getLocality().equals(locality)) {
                        rightInstitution.add(institution);
                    }
                }

        }
        return rightInstitution;
    }

    public void setInstitutionChoiceBoxForService(ChoiceBox policeChoiceBox, ChoiceBox emergencyChoiceBox, ChoiceBox fireBrigadeChoiceBox) {
        this.policeChoiceBox = policeChoiceBox;
        this.emergencyChoiceBox = emergencyChoiceBox;
        this.fireBrigadeChoiceBox = fireBrigadeChoiceBox;
        setStringNameInstitution(policeChoiceBox,emergencyChoiceBox,fireBrigadeChoiceBox);
    }

    public void setStringNameInstitution(ChoiceBox policeChoiceBox, ChoiceBox emergencyChoiceBox, ChoiceBox fireBrigadeChoiceBox){
        this.policeNameInstitution = (String) policeChoiceBox.getSelectionModel().getSelectedItem();
        this.emergencyNameInstitution = (String) emergencyChoiceBox.getSelectionModel().getSelectedItem();
        this.fireBrigadeNameInstitution = (String) fireBrigadeChoiceBox.getSelectionModel().getSelectedItem();
    }


    public String getPoliceNameInstitution() {
        return policeNameInstitution;
    }

    public String getEmergencyNameInstitution() {
        return emergencyNameInstitution;
    }

    public String getFireBrigadeNameInstitution() {
        return fireBrigadeNameInstitution;
    }


    public void saveFirstNotification(MessageWithNotification messageWithNotification) {
        Message message = new Message.MessageBuilder(MessageType.SAVE_NEW_FIRST_NOTIFICATION)
                .object(messageWithNotification)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();

    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }


    public void saveSecondNotification(SecondMessageWithNotification messageWithNotification) {
        messageWithNotification.setIdFirstMessage(notificationId);
        Message message = new Message.MessageBuilder(MessageType.SAVE_NEW_SECOND_NOTIFICATION)
                .object(messageWithNotification)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();

    }

    public boolean returnResultOfSaveAllNotificationInDatabase(){
        return this.resultNotificationInServer;
    }

    public void setResultNotificationInServer(Boolean resultNotificationInServer) {
        System.out.println("utytane");
        this.resultNotificationInServer = resultNotificationInServer;
    }
}
