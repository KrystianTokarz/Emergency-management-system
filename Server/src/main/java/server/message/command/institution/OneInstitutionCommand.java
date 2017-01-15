package server.message.command.institution;

import server.fasade.InstitutionFacade;
import server.message.command.Command;
import server.model.institution.Institution;
import server.repository.InstitutionRepository;

public class OneInstitutionCommand implements Command<Institution>{

    InstitutionFacade facade = new InstitutionFacade();
    @Override
    public <S> Institution execute(S param) {
        return facade.findInstitutionByName((Institution) param);
    }
}
