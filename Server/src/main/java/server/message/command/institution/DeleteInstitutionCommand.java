package server.message.command.institution;

import server.fasade.EmployeesFacade;
import server.fasade.InstitutionFacade;
import server.message.command.Command;
import server.model.employee.Employee;
import server.model.institution.Institution;

import java.util.List;

/**
 * Command Class for delete Employee from DB
 */
public class DeleteInstitutionCommand implements Command<List<Institution>> {

    private InstitutionFacade facade = InstitutionFacade.getInstance();

    @Override
    public <S> List<Institution> execute(S param) {

        return facade.deleteInstitution((List<Institution>) param);
    }
}
