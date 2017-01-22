package server.message.command.institution;

import server.fasade.InstitutionFacade;
import server.message.command.Command;
import server.model.institution.Institution;
import server.repository.InstitutionRepository;

import java.util.List;

public class SaveInstitutionCommand implements Command<List<Institution>> {

    private InstitutionFacade facade = InstitutionFacade.getInstance();
    @Override
    public <S> List<Institution> execute(S param) {
        return facade.saveNewInstitution((Institution) param);
    }
}
