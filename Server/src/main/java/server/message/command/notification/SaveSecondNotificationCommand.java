package server.message.command.notification;

import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.message.SecondMessageWithNotification;

public class SaveSecondNotificationCommand implements Command<Boolean> {

    private NotificationFacade notificationFacade = NotificationFacade.getInstance();

    @Override
    public <S> Boolean execute(S param) {
         return notificationFacade.saveSecondNotification((SecondMessageWithNotification) param);
    }
}
