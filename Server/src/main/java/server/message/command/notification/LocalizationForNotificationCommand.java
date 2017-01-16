package server.message.command.notification;

import server.fasade.LocalizationFacade;
import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.localization.Province;

import java.util.List;

public class LocalizationForNotificationCommand implements Command<List<Province>> {


    private LocalizationFacade localizationFacade = new LocalizationFacade();

    @Override
    public <S> List<Province> execute(S param) {
        return localizationFacade.findAllProvince();
    }


}
