package server.gui.administrator.employeeManagement.flyweight.factory;

import javafx.scene.image.Image;
import server.gui.administrator.employeeManagement.flyweight.ImageFlyweight;
import server.gui.administrator.employeeManagement.flyweight.SharedImageFlyweight;
import server.gui.administrator.employeeManagement.flyweight.UnsharedImageFlyweight;

/**
 * Factory (pattern) for create right image
 */
public class ImageFlyweightFactory {

    public ImageFlyweight createCorrectImageFlyweight(byte[] imageByteArray){
        if (imageByteArray == null)
            return new UnsharedImageFlyweight();
        else
            return new SharedImageFlyweight(imageByteArray);
    }
}
