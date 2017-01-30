package server.message.command;

import server.model.message.MessageType;

import java.util.HashMap;
import java.util.Map;


/**
 * Command register class with map (key-> type of message value-> right command element)
 */
public class CommandRegister {

    private Map<MessageType, Command> map = new HashMap<>();

    public void addCommand(MessageType messageType, Command c){
        map.put(messageType, c);
    }

    public Command getCommand(MessageType messageType){
        return map.containsKey(messageType) ? map.get(messageType) : null;
    }
}
