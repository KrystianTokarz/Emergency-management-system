package server.message.command.institutions;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;

/**
 * Command pattern element
 */
public class AllInstitutionListCommand implements Command {

    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setAllInstitutionList();
    }
}
