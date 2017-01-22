package server.message.command.institutions;

import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.DistributorCommandMediator;
import server.model.institution.Institution;
import server.model.localization.Province;

import java.util.List;

public class InstitutionsForNotificationCommand implements Command {


    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setInstitutionForServer();
    }
}
