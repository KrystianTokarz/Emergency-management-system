package server.message.command.institutions;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;
import server.model.institution.Institution;
import server.model.localization.Province;

import java.util.List;

public class InstitutionsForNotificationCommand implements Command {

    private DistributorCommandMediator commandMediator;


    public InstitutionsForNotificationCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerInstitutionForNotificationCommand(this);
    }
    @Override
    public <S> void execute(S param) {
        commandMediator.setInstitutionForServer((List<Institution>) param);
    }
}
