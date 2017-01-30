package server.communication;

import server.message.*;
import server.message.chaingOfResponsibility.AbstractChainElement;


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
    private Message messageFromClient;
    private Message messageToClient;
    private AbstractChainElement chainOfElements;
//    Logger logger = Logger.getLogger(this.getClass().getName());

    public EchoThread(Socket clientSocket, AbstractChainElement chainOfElements) {
        this.socket = clientSocket;
        this.chainOfElements = chainOfElements;
        try {
            streamInput = new ObjectInputStream(socket.getInputStream());
            streamOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while ((messageFromClient = (Message) streamInput.readObject()) != null) {
                messageToClient = chainOfElements.processRequest(messageFromClient);
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
}

