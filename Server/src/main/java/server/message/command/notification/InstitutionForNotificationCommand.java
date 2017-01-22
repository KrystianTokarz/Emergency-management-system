package server.message.command.notification;

import server.fasade.InstitutionFacade;
import server.message.command.Command;
import server.model.institution.Institution;

import java.util.List;

public class InstitutionForNotificationCommand implements Command<List<Institution>> {

    private InstitutionFacade facade = InstitutionFacade.getInstance();

    @Override
    public <S> List<Institution> execute(S param) {
        System.out.println("pyyykus");
        return facade.findAllInstitutions();
    }
}
