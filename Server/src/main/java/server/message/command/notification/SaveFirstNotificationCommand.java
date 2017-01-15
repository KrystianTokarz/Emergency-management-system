package server.message.command.notification;

import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.message.MessageWithNotification;

public class SaveFirstNotificationCommand implements Command<Long> {

    private NotificationFacade notificationFacade = new NotificationFacade();

    @Override
    public <S> Long execute(S param) {
        return notificationFacade.saveFirstNotification((MessageWithNotification) param);
    }
}
