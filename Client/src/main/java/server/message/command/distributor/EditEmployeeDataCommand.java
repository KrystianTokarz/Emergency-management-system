package server.message.command.distributor;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;

/**
 * Command pattern element
 */
public class EditEmployeeDataCommand implements Command{

    @Override
    public void execute() {
            ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
            clientFacadeProxy.setProgramPanelInformation();
    }
}
