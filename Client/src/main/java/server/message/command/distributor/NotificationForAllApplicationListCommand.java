package server.message.command.distributor;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;

public class NotificationForAllApplicationListCommand implements Command {

    private DistributorCommandMediator commandMediator;

    public NotificationForAllApplicationListCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerNotificationForApplicationListCommand(this);
    }
    @Override
    public <S> void execute(S param) {
        commandMediator.setNotificationForDistributor(param);
    }
}
