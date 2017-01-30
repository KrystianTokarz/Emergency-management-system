package server.message.command.distributor;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;

public class OneEmployeeForDistributorCommand implements Command{

    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.giveEmployeeDataForDistributor();
    }
}
