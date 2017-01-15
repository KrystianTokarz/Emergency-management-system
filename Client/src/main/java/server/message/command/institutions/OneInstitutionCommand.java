package server.message.command.institutions;

import server.message.command.Command;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;
import server.model.employee.Employee;
import server.model.institution.Institution;

public class OneInstitutionCommand implements Command {
    private Mediator commandMediator;

    public OneInstitutionCommand(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerOneInstitutionCommand(this);
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.setInstitutionFromServer((Institution) param);
    }
}
