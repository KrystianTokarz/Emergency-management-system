package server.message.command.employees;

import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

import java.util.List;
import java.util.Map;

/**
 * Command Class for edit Employee from DB
 */
public class EditEmployeeCommand implements Command<List<Employee>> {

    private EmployeesFacade fasade = new EmployeesFacade();

    @Override
    public <S> List<Employee> execute(S param) {
        return fasade.updateEmployee((Map<String , Employee>) param);
    }
}
