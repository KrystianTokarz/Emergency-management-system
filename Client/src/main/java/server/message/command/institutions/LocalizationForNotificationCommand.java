package server.message.command.institutions;

import server.message.command.Command;
import server.message.mediator.DistributorCommandMediator;
import server.model.localization.Province;

import java.util.List;

public class LocalizationForNotificationCommand implements Command{

    private DistributorCommandMediator commandMediator;


    public LocalizationForNotificationCommand(DistributorCommandMediator commandMediator){
        this.commandMediator = commandMediator;
        commandMediator.registerLocalizationForNotificationCommand(this);
    }
    @Override
    public <S> void execute(S param) {
        commandMediator.setLocalizationForServer((List<Province>) param);
    }
}
