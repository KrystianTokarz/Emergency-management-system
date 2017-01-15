package server.message.command;

import server.model.message.MessageType;

import java.util.HashMap;
import java.util.Map;

public class CommandRegister {

    Map<MessageType, Command> map = new HashMap<>();

    public void addCommand(MessageType messageType, Command c){
        map.put(messageType, c);
    }

    public Command getCommand(MessageType messageType){
        return map.containsKey(messageType) ? map.get(messageType) : null;
    }

}
