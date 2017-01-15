package server.gui.administrator.employeeManagement.flyweight;

import server.model.employee.EmployeeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;


public class UnsharedImageFlyweight implements ImageFlyweight {

    private byte[] employeeImage;

    public UnsharedImageFlyweight(){
//        try {
//            BufferedImage image =
//                    ImageIO.read(new File("c:\\image\\mypic.jpg"));
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write( image, "jpg", baos );
//            baos.flush();
//            employeeImage = baos.toByteArray();
//            baos.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
