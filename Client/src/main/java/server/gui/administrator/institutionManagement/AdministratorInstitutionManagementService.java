package server.gui.administrator.institutionManagement;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.communication.EchoThread;
import server.gui.administrator.institutionManagement.flyweight.ImageFlyweight;
import server.gui.administrator.institutionManagement.flyweight.factory.ImageFlyweightFactory;
import server.message.Message;
import server.message.mediator.CommandMediator;
import server.model.institution.Institution;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.ProvinceType;
import server.model.message.MessageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorInstitutionManagementService {

    private CommandMediator commandMediator;
    private List<Institution> allInstitutionList;
    private ImageFlyweightFactory imageFlyweightFactory;
    private Timeline timeline = new Timeline();



    public AdministratorInstitutionManagementService(CommandMediator commandMediator) {
        this.commandMediator = commandMediator;
        this.imageFlyweightFactory = new ImageFlyweightFactory();
    }


    public void setAllInstitutionList(Object allInstitutionList) {
        this.allInstitutionList = (List<Institution>) allInstitutionList;

    }
    public List<Institution> getRightInstitutionListForProvince(ProvinceType type){
        if (type == null){
            return allInstitutionList;
        }
        List<Institution> rightInstitution = new ArrayList<>();
        for (Institution institution: allInstitutionList) {
            if(institution.getProvince().getProvinceType().equals(type)){
                rightInstitution.add(institution);
            }
        }
        return rightInstitution;
    }

    public List<Institution> getRightInstitutionListForLocality(ObservableList<InstitutionForTable> convertedInstitutionForTables,String specialValue){
        List<Institution> rightInstitution = new ArrayList<>();
        for (Institution institution: allInstitutionList) {
            for (InstitutionForTable institutionForTable:convertedInstitutionForTables) {
                if(institutionForTable.getName().equals(institution.getName())) {
                    if (specialValue.equals("All")) {
                        rightInstitution.add(institution);
                    } else {
                        Locality tmpLocality = institution.getLocality();
                        {
                            if (tmpLocality.getLocality().equals(specialValue)) {
                                rightInstitution.add(institution);
                            }
                        }
                    }
                }
            }

        }
        return rightInstitution;
    }

    public List<Institution> getRightInstitutionListForType(String type,String provinceComboBoxValue,String localityComboBoxValue){
        List<Institution> rightInstitution = new ArrayList<>();
        InstitutionType institutionType = null;
        ProvinceType provinceType = null;
        if(localityComboBoxValue== null || localityComboBoxValue.equals("All")){
            localityComboBoxValue = null;
        }

        if(provinceComboBoxValue == null || provinceComboBoxValue.equals("All"))
            provinceType = null;
        else if(provinceComboBoxValue.equals("Swietokrzyskie"))
            provinceType = ProvinceType.SWIETOKRZYSKIE;
        else  if(provinceComboBoxValue.equals("Lodzkie"))
            provinceType = ProvinceType.LODZKIE;
        else  if(provinceComboBoxValue.equals("Maslovia"))
            provinceType = ProvinceType.MASLOVIA;




        if(type.equals("All"))
            institutionType = null;
        else if(type.equals("Fire Brigade"))
            institutionType = InstitutionType.FIRE_BRIGADE;
        else if(type.equals("Police"))
            institutionType = InstitutionType.POLICE;
        else if(type.equals("Emergency"))
            institutionType = InstitutionType.EMERGENCY;


//        List<Institution> tmpInstitutionList = new ArrayList<>();

        for (Institution institution: allInstitutionList) {
            if(( institutionType == null || institution.getInstitutionType() == institutionType) && (provinceType == null || institution.getProvince().getProvinceType() == provinceType)) {
                if(localityComboBoxValue!= null ) {
                   // for (Locality locality : institution.getProvince().getLocalityList()) {
                        if (institution.getLocality().getLocality().equals(localityComboBoxValue))
                            rightInstitution.add(institution);
                   // }
                }else{
                            rightInstitution.add(institution);
                }
            }
        }

        return rightInstitution;
    }

    public List<Locality> getLocalityList(Institution institution){
        return institution.getProvince().getLocalityList();
    }

    public ObservableList<InstitutionForTable> sortWithInstitutionType(ObservableList<InstitutionForTable> convertedInstitutionForTables,String type){
        ObservableList<InstitutionForTable> tmpInstitutionToReturn = FXCollections.observableArrayList();

        for (InstitutionForTable institution: convertedInstitutionForTables) {
            if(institution.getType().equals(type))
                tmpInstitutionToReturn.add(institution);
        }
        return tmpInstitutionToReturn;
    }


    public ObservableList<InstitutionForTable> convertInstitutionListIntoInstitutionForTableList(List<Institution> allInstitutionList) {
        if(allInstitutionList == null)
            return null;

        ObservableList<InstitutionForTable> institutionsList= FXCollections.observableArrayList();
        for (Institution institution: allInstitutionList) {
            InstitutionForTable institutionForTable = new InstitutionForTable();

            ImageFlyweight correctImageFlyweight;
            if(institution.getInstitutionImage()== null) {
                correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(null);
            }
            else {
                correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(institution.getInstitutionImage().getImage());
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(correctImageFlyweight.getCorrectEmployeeImage());
            try {
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                institutionForTable.setImage(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }


            institutionForTable.setName(institution.getName());
            institutionForTable.setAvailability(institution.getAvailability());
            if (institution.getInstitutionType() == InstitutionType.EMERGENCY)
                institutionForTable.setType("Emergency");
            else if (institution.getInstitutionType() == InstitutionType.FIRE_BRIGADE)
                institutionForTable.setType("Fire Brigade");
            else
                institutionForTable.setType("Police");
            institutionsList.add(institutionForTable);
        }
        return institutionsList;
    }

    public ObservableList<InstitutionForTable> loadInstitutionTable() {

        if(allInstitutionList == null){
            return null;
        }


        ObservableList<InstitutionForTable> institutionsList= FXCollections.observableArrayList();
        for (Institution institution: allInstitutionList) {
            InstitutionForTable institutionForTable = new InstitutionForTable();

            ImageFlyweight correctImageFlyweight;
            if(institution.getInstitutionImage()== null) {
                correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(null);
            }
            else {
                correctImageFlyweight = imageFlyweightFactory.createCorrectImageFlyweight(institution.getInstitutionImage().getImage());
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(correctImageFlyweight.getCorrectEmployeeImage());
            try {
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                institutionForTable.setImage(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }


            institutionForTable.setName(institution.getName());
            institutionForTable.setAvailability(institution.getAvailability());
            if (institution.getInstitutionType() == InstitutionType.EMERGENCY)
                institutionForTable.setType("Emergency");
            else if (institution.getInstitutionType() == InstitutionType.FIRE_BRIGADE)
                institutionForTable.setType("Fire Brigade");
            else
                institutionForTable.setType("Police");
            institutionsList.add(institutionForTable);
        }
        return institutionsList;
    }

    public Timeline give10SecondTimelineForInstitution() {
        return timeline;
    }

    public void deleteInstitutions(ObservableList<InstitutionForTable> institutionList) {

        List<Institution> listForServer = new ArrayList<>();

        for(InstitutionForTable inst : institutionList){
            Institution institutionForServer = new Institution();
            institutionForServer.setName(inst.getName());
            listForServer.add(institutionForServer);
        }

        Message message = new Message.MessageBuilder(MessageType.DELETE_INSTITUTIONS)
                .object(listForServer)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void sendForInstitutionDataToEdit(Institution institution) {
        Message message = new Message.MessageBuilder(MessageType.SEND_INSTITUTION)
                .object(institution)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }
}
