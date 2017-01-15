package server.message.command.employees;

import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

import java.util.List;

/**
 * Command Class for delete Employee from DB
 */
public class DeleteEmployeeCommand implements Command<List<Employee>> {

    private EmployeesFacade fasade = new EmployeesFacade();

    @Override
    public <S> List<Employee> execute(S param) {
        return fasade.deleteEmployee((List<Employee>) param);
    }
}
