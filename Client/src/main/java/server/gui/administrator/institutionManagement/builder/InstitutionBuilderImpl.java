package server.gui.administrator.institutionManagement.builder;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import server.model.employee.EmployeeImage;
import server.model.institution.Institution;
import server.model.institution.InstitutionImage;
import server.model.institution.InstitutionType;
import server.model.localization.Locality;
import server.model.localization.Province;
import server.model.localization.ProvinceType;
import server.model.localization.Street;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Institution builder implementation
 */
public class InstitutionBuilderImpl implements  InstitutionBuilder {
    private Institution institution;

    public InstitutionBuilderImpl(){
        this.institution = new Institution();
    }

    @Override
    public void setInstitutionName(String institutionName) {
        institution.setName(institutionName);
    }

    @Override
    public void setInstitutionType(String institutionType) {
        if(institutionType.equals("Fire Brigade"))
            institution.setInstitutionType(InstitutionType.FIRE_BRIGADE);
        else if (institutionType.equals("Police"))
            institution.setInstitutionType(InstitutionType.POLICE);
        else if (institutionType.equals("Emergency"))
            institution.setInstitutionType(InstitutionType.EMERGENCY);

    }

    @Override
    public void setInstitutionImage(ImageView image) {
        if( image.getImage() != null) {
            byte[] byteArrayImage = null;
            BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            try {
                ImageIO.write(bImage, "png", s);
                byteArrayImage = s.toByteArray();
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            InstitutionImage institutionImage = new InstitutionImage();
            institutionImage.setImage(byteArrayImage);
            institution.setInstitutionImage(institutionImage);
        }
        else
            institution.setInstitutionImage(null);
    }

    @Override
    public void setAvailability(boolean availability) {
            institution.setAvailability(availability);
    }

    @Override
    public void  setLocality(String province,String locality,String street, String specialNumber) {

        Street newStreet = new Street();
        newStreet.setStreet(street);
        newStreet.setSpecialNumber(specialNumber);

        Locality newLocality = new Locality();
        newLocality.setLocality(locality);
        newLocality.add(newStreet);

        Province newProvince = new Province();
        if(province.equals("Swietokrzyskie"))
            newProvince.setProvinceType(ProvinceType.SWIETOKRZYSKIE);
        else if (province.equals("Lodzkie"))
            newProvince.setProvinceType(ProvinceType.LODZKIE);
        else if (province.equals("Maslovia"))
            newProvince.setProvinceType(ProvinceType.MASLOVIA);
        newProvince.add(newLocality);

        institution.setProvince(newProvince);
        institution.setLocality(newLocality);
        institution.setStreet(newStreet);
    }

    @Override
    public Institution getResult() {
        return this.institution;
    }
}
