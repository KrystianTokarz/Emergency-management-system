package server.message.command.employees;


import server.message.command.Command;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;

import java.util.List;

public class EmployeeListCommand implements Command {


    private Mediator commandMediator;

    public EmployeeListCommand(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerEmployeeListCommand(this);
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.setEmployeeList(param);
    }
}
