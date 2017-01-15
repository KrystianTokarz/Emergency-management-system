package server.message.command.employees;

import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

/**
 * Command Class for find Employee by their first and last name and their mail
 */
public class OneEmployeeCommand implements Command<Employee> {

    private EmployeesFacade fasade = new EmployeesFacade();

    @Override
    public <S> Employee execute(S param) {

       return fasade.findEmployeeByFirstAndLastNameAndMail((Employee) param);
    }
}
