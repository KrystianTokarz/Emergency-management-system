package server.message.command.distributor;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.DistributorCommandMediator;

public class SaveFirstNotificationCommand implements Command {



    @Override
    public  void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setNotificationId();
    }
}
