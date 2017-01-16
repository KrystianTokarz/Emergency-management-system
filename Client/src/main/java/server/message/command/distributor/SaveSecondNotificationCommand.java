package server.message.command.distributor;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;

public class SaveSecondNotificationCommand implements Command {

    private DistributorCommandMediator commandMediator;

    public SaveSecondNotificationCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.setResultNotificationInServer((Boolean) param);
    }
}
