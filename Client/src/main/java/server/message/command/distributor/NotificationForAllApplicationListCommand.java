package server.message.command.distributor;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.DistributorCommandMediator;

/**
 * Command pattern element
 */
public class NotificationForAllApplicationListCommand implements Command {

    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setNotificationForDistributor();
    }
}
