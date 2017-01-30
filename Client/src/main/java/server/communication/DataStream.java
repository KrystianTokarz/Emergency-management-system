package server.communication;

import java.io.*;
import java.net.Socket;

/**
 * Singleton class with socket and input/output stream
 */
public class DataStream {
    private static DataStream instance = null;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public static DataStream getInstance() {
        if (instance == null) {
            instance = new DataStream();
        }
        return instance;
    }

    private DataStream() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }
}
