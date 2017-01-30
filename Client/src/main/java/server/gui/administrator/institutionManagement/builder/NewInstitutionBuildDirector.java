package server.gui.administrator.institutionManagement.builder;

import javafx.scene.image.ImageView;
import server.model.institution.Institution;

/**
 * Institution builder director
 */
public class NewInstitutionBuildDirector {

    private InstitutionBuilder institutionBuilder;

    public NewInstitutionBuildDirector(InstitutionBuilder institutionBuilder){
        this.institutionBuilder = institutionBuilder;
    }

    public Institution construct(String institutionName, String institutionType, String provinceType, String localityType, String streetName, String numberName, ImageView imageView, boolean selectedValue){
        institutionBuilder.setInstitutionName(institutionName);
        institutionBuilder.setAvailability(selectedValue);
        institutionBuilder.setInstitutionImage(imageView);
        institutionBuilder.setInstitutionType(institutionType);
        institutionBuilder.setLocality(provinceType,localityType,streetName,numberName);
        return  institutionBuilder.getResult();
    }
}
