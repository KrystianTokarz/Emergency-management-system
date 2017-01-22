package server.message.command.institutions;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;
import server.model.institution.Institution;

public class OneInstitutionCommand implements Command {

    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setInstitutionFromServer();
    }
}
