package server.message.command.employees;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;


/**
 * Command pattern element
 */
public class OneEmployeeForDistributorCommand implements Command {

    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.giveEmployeeDataForDistributor();
    }
}
