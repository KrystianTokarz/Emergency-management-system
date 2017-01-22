package server.message.command.notification;

import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.message.FirstMessageWithNotification;

public class SaveFirstNotificationCommand implements Command<Long> {

    private NotificationFacade notificationFacade = NotificationFacade.getInstance();

    @Override
    public <S> Long execute(S param) {
        return notificationFacade.saveFirstNotification((FirstMessageWithNotification) param);
    }
}
