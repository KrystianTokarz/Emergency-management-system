package server.message.command;

import server.message.Message;
import server.message.facade.ClientFacadeProxy;
import server.model.message.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Command invoker with list of messages from server and thread which support this list
 */
public class CommandInvoker {

    private CommandRegister commandRegister;
    private List<Message> commandList = new ArrayList<>();
    private CommandExecutorThread commandExecutorThread;

    public CommandInvoker(CommandRegister cR){
        this.commandRegister=cR;
        commandExecutorThread = new CommandExecutorThread();
        commandExecutorThread.setDaemon(true);
        commandExecutorThread.start();

    }

    public void takeCommand(Message message){
        synchronized (commandExecutorThread){
            commandList.add(message);
            if (commandExecutorThread.getState() == Thread.State.WAITING || commandExecutorThread.getState() == Thread.State.TIMED_WAITING)
                commandExecutorThread.notify();
        }
    }



    private class CommandExecutorThread extends Thread{
        @Override
        public void run() {
            while(true){
                synchronized (commandExecutorThread) {
                    if(commandList.size()<=0) {
                            try {
                                commandExecutorThread.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    }
                    Message removeMessage = commandList.remove(0);
                    ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
                    clientFacadeProxy.setObjectForMediator(removeMessage.getObject());
                    Command command = commandRegister.getCommand(removeMessage.getType());
                    command.execute();
                }
            }
        }
    }
}
