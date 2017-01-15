package server.message.command.employees;

import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

import java.util.List;
/**
 * Command Class for save new Employee
 */
public class SaveEmployeeCommand implements Command<List<Employee>> {

    private EmployeesFacade fasade = new EmployeesFacade();

    @Override
    public <S> List<Employee> execute(S param) {
        return fasade.saveNewEmployee((Employee) param);
    }
}
