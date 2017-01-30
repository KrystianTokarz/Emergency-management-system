package server.gui.administrator.employeeManagement.flyweight;

import server.model.employee.EmployeeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;

/**
 * Class for unshared image (Flyweight pattern)
 */
public class UnsharedImageFlyweight implements ImageFlyweight {

    private byte[] employeeImage;

    public UnsharedImageFlyweight(){

        Path path = Paths.get("src\\main\\resources\\images\\employee-image.png");
        try {
            employeeImage = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public byte[] getCorrectEmployeeImage() {
        return this.employeeImage;
    }
}
