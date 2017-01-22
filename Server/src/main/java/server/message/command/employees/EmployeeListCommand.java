package server.message.command.employees;

import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

import java.util.List;

/**
 * Command Class for find all Employees in application system
 */
public class EmployeeListCommand implements Command<List<Employee>> {

    private EmployeesFacade fasade = EmployeesFacade.getInstance();

    @Override
        public <S> List<Employee> execute(S param) {
        return fasade.findAllEmployeesForAdministrator();
    }
}
