package server.gui.administrator.institutionManagement.flyweight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for unshared image (Flyweight pattern)
 */
public class UnsharedImageFlyweight implements ImageFlyweight {

    private byte[] institutionImage;

    public UnsharedImageFlyweight(){

        Path path = Paths.get("src\\main\\resources\\images\\institution-image.png");
        try {
            institutionImage = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public byte[] getCorrectInstitutionImage() {
        return this.institutionImage;
    }
}
