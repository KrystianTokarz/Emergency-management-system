package server.message.command.distributor;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;

public class OneEmployeeForDistributorCommand implements Command{

    private DistributorCommandMediator commandMediator;

    public OneEmployeeForDistributorCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }


    @Override
    public <S> void execute(S param) {

        commandMediator.giveEmployeeData((Employee) param);
    }
}
