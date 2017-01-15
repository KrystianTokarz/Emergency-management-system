package server.message.command.employees;

import server.message.command.Command;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;

public class OneEmployeeCommand implements Command {
    private Mediator commandMediator;

    public OneEmployeeCommand(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerOneEmployeeCommand(this);
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.giveEmployeeData((Employee) param);
    }
}
