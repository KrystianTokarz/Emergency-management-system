package server.gui.administrator.institutionManagement.flyweight.factory;

import server.gui.administrator.institutionManagement.flyweight.ImageFlyweight;
import server.gui.administrator.institutionManagement.flyweight.SharedImageFlyweight;
import server.gui.administrator.institutionManagement.flyweight.UnsharedImageFlyweight;


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
