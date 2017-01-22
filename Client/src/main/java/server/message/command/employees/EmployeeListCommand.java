package server.message.command.employees;


import server.message.command.Command;
import server.message.facade.ClientFacadeProxy;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;

import java.util.List;

public class EmployeeListCommand implements Command {




    @Override
    public void execute() {
        ClientFacadeProxy clientFacadeProxy = ClientFacadeProxy.getInstance();
        clientFacadeProxy.setEmployeeList();
    }
}
