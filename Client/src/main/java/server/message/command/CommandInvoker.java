package server.message.command;

import server.message.Message;
import server.model.message.MessageType;

public class CommandInvoker {

    private CommandRegister commandRegister;
    private Message msg;

    public CommandInvoker(CommandRegister cR,Message msg){
        this.commandRegister=cR;
        this.msg=msg;
    }

    public void execute(MessageType messageType){
       Command cmd =commandRegister.getCommand(messageType);
       cmd.execute(msg.getObject());
    }
}
