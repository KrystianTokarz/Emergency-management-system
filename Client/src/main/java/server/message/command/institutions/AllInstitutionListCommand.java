package server.message.command.institutions;

import server.message.command.Command;
import server.message.mediator.CommandMediator;
import server.message.mediator.Mediator;

public class AllInstitutionListCommand implements Command {

    private Mediator commandMediator;

    public AllInstitutionListCommand(CommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerAllInstitutionListCommand(this);
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.setAllInstitutionList(param);
    }
}
