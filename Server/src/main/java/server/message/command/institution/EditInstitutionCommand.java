package server.message.command.institution;

import server.fasade.InstitutionFacade;
import server.message.command.Command;
import server.model.employee.Employee;
import server.model.institution.Institution;

import java.util.List;
import java.util.Map;

public class EditInstitutionCommand implements Command<List<Institution>> {

    private  InstitutionFacade facade = new InstitutionFacade();
    @Override
    public <S> List<Institution> execute(S param) {
        return facade.updateInstitution((Map<String , Institution>) param);
    }
}
