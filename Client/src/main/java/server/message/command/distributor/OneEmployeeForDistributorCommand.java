package server.message.command.distributor;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.DistributorCommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;

public class OneEmployeeForDistributorCommand implements Command{




    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.giveEmployeeData();
    }
}
