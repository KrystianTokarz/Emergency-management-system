package server.gui.administrator.institutionManagement.flyweight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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

        Path path = Paths.get("src\\main\\resources\\images\\institution-image.png");
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
