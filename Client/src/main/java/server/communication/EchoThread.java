package server.communication;

import server.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class EchoThread extends Thread{

    private ObjectOutputStream streamOutput;
    private Message messageToServer;
    private DataStream dataSocket = DataStream.getInstance();

    public  EchoThread( Message message){
        this.streamOutput = dataSocket.getOutputStream();
        this.messageToServer = message;
    }

    @Override
    public void run() {
        try {
            streamOutput.writeObject(messageToServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
