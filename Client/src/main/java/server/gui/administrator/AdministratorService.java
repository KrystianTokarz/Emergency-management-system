package server.gui.administrator;

import server.communication.DataStream;
import server.communication.EchoThread;
import server.message.Message;
import server.message.mediator.CommandMediator;
import server.model.message.MessageType;

import java.io.IOException;

public class AdministratorService {

    private Message message;
    private String firstName;
    private String lastName;
    private byte[] image;

    private CommandMediator commandMediator;

    public AdministratorService(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String loadEmployeeFirstAndLastName(){
        String firstName = commandMediator.getUserFirstName();
        String lastName = commandMediator.getUserLastName();
        return firstName + "  " + lastName;
    }

    public byte[] loadEmployeeImageView(){
        byte[] userImage = commandMediator.getUserImage();
        System.out.println(userImage);
        return userImage;
    }

    public void logout(){
        DataStream dataStream = DataStream.getInstance();
        try {
            dataStream.getSocket().shutdownInput();
            dataStream.getSocket().shutdownOutput();
            dataStream.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessageForEmployeeList(){

        message = new Message.MessageBuilder(MessageType.EMPLOYEE_LIST)
                .object(null)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }

    public void sendMessageForInstitutionList() {
        message = new Message.MessageBuilder(MessageType.ALL_INSTITUTION_LIST)
                .object(null)
                .build();
        Thread thread = new EchoThread(message);
        thread.start();
    }
}

