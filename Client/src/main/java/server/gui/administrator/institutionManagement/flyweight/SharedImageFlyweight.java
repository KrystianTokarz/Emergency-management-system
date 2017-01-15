package server.gui.administrator.institutionManagement.flyweight;

public class SharedImageFlyweight  implements ImageFlyweight {

    private byte[] employeeImage;

    public SharedImageFlyweight(byte[] employeeImage){
        this.employeeImage = employeeImage;
    }

    @Override
    public byte[] getCorrectEmployeeImage() {
        return employeeImage;
    }
}
