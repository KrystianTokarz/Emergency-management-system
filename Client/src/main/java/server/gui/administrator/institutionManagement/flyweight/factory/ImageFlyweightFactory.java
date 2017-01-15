package server.gui.administrator.institutionManagement.flyweight.factory;

import server.gui.administrator.institutionManagement.flyweight.ImageFlyweight;
import server.gui.administrator.institutionManagement.flyweight.SharedImageFlyweight;
import server.gui.administrator.institutionManagement.flyweight.UnsharedImageFlyweight;

public class ImageFlyweightFactory {

//    private ImageFlyweight imageFlyweight;
//
//    public ImageFlyweightFactory(ImageFlyweight imageFlyweight){
//        this.imageFlyweight = imageFlyweight;
//    }

    public ImageFlyweight createCorrectImageFlyweight(byte[] imageByteArray){
        if (imageByteArray == null)
            return new UnsharedImageFlyweight();
        else
            return new SharedImageFlyweight(imageByteArray);
    }
}
