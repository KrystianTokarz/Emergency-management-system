package server.gui.administrator.institutionManagement.builder;

import javafx.scene.image.ImageView;
import server.model.institution.Institution;


/**
 * Builder pattern interface for build institution
 */
public interface InstitutionBuilder {

    Institution getResult();
    void setInstitutionName(String institutionName);
    void setInstitutionType(String institutionType);
    void setInstitutionImage(ImageView imageView);
    void setAvailability(boolean availability);
    void setLocality(String province,String locality,String street, String specialNumber);
}
