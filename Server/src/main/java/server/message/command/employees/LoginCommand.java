package server.message.command.employees;


import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;
import server.model.employee.EmployeeAccount;

/**
 * Command class for login user into application system
 */
public class LoginCommand implements Command<Employee> {

    private EmployeesFacade fasade = EmployeesFacade.getInstance();

    @Override
    public <S> Employee execute(S param) {
        return fasade.findEmployeeByLoginAndPassword((EmployeeAccount) param);
    }
}
