package server.message.command;

import server.message.Message;
import server.model.message.MessageType;

/**
 * Special class belonging to the command pattern
 * Responsible for execute right command and return their result
 */
public class CommandInvoker {

    private CommandRegister commandRegister;
    private Message msg;

    public CommandInvoker(CommandRegister cR,Message msg){
        this.commandRegister=cR;
        this.msg=msg;
        }

    public Object execute(MessageType messageType){
       Command cmd =commandRegister.getCommand(messageType);
       return cmd.execute(msg.getObject());
    }
}
