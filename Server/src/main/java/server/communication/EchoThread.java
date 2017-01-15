package server.communication;

import server.message.*;
import server.message.command.CommandInvoker;
import server.message.command.CommandRegister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;


/**
 *Main class extends Thread which supports each client
 */
public class EchoThread extends Thread {
    private Socket socket;
    private ObjectInputStream streamInput;
    private ObjectOutputStream streamOutput;
    private CommandRegister commandRegister;
    private Message messageFromClient;
    private Message messageToClient;
    private CommandInvoker commandInvoker;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public EchoThread(Socket clientSocket,CommandRegister commandRegister) {
        this.commandRegister = commandRegister;
        this.socket = clientSocket;
        try {
            streamInput = new ObjectInputStream(socket.getInputStream());
            streamOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        logger.info("przyszlo");
       try {
           while ((messageFromClient = (Message) streamInput.readObject()) != null) {
               messageToClient = processMessage(messageFromClient);
               streamOutput.writeObject(messageToClient);

           }
       } catch (IOException | ClassNotFoundException e) {
           try {
               streamInput.close();
               streamOutput.close();
               socket.close();
           } catch (IOException e1) {
               e1.printStackTrace();
           }
       }
    }

    private Message processMessage(Message messageFromServer) {

        commandInvoker = new CommandInvoker(commandRegister,messageFromClient);

        Object object = commandInvoker.execute(messageFromClient.getType());
        return new Message.MessageBuilder(messageFromServer.getType())
                .object(object)
                .build();
    }
}

