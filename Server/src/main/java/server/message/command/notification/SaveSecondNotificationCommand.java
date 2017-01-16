package server.message.command.notification;

import org.mockito.internal.matchers.Null;
import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.message.MessageWithNotification;
import server.model.message.SecondMessageWithNotification;

public class SaveSecondNotificationCommand implements Command<Boolean> {

    private NotificationFacade notificationFacade = new NotificationFacade();

    @Override
    public <S> Boolean execute(S param) {
        System.out.println("PRYSZLOO :D ");
         return notificationFacade.saveSecondNotification((SecondMessageWithNotification) param);
    }
}
