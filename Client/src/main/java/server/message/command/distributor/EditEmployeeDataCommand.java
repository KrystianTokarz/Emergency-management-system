package server.message.command.distributor;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;
import server.model.employee.Employee;

public class EditEmployeeDataCommand implements Command{

    private DistributorCommandMediator commandMediator;

    public EditEmployeeDataCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }
    @Override
    public <S> void execute(S param) {
            commandMediator.setProgramPanelInformation((Employee) param);
    }
}
