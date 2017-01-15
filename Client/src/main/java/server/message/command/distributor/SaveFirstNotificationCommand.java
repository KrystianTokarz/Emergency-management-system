package server.message.command.distributor;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;

public class SaveFirstNotificationCommand implements Command {

    private DistributorCommandMediator commandMediator;

    public SaveFirstNotificationCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
    }

    @Override
    public <S> void execute(S param) {
        commandMediator.setNotificationId((Long) param);
        System.out.println("przyszlo");
    }
}
