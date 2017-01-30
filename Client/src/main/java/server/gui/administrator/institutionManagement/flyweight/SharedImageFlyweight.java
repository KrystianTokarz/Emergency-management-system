package server.gui.administrator.institutionManagement.flyweight;

/**
 * Class for shared image (Flyweight pattern)
 */
public class SharedImageFlyweight  implements ImageFlyweight {

    private byte[] institutionImage;

    public SharedImageFlyweight(byte[] employeeImage){
        this.institutionImage = employeeImage;
    }

    @Override
    public byte[] getCorrectInstitutionImage() {
        return institutionImage;
    }
}
