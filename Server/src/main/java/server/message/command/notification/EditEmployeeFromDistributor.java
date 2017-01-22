package server.message.command.notification;

import org.mockito.internal.matchers.Null;
import server.fasade.EmployeesFacade;
import server.message.command.Command;
import server.model.employee.Employee;

import java.util.Map;

public class EditEmployeeFromDistributor  implements Command<Employee> {

    private EmployeesFacade fasade = EmployeesFacade.getInstance();
    @Override
    public <S> Employee execute(S param) {

        return fasade.updateEmployeeFromDistributor((Map<String , Employee>) param);

    }
}
