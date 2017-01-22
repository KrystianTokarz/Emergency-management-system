package server.message.command.notification;

import server.fasade.NotificationFacade;
import server.message.command.Command;
import server.model.employee.Employee;
import server.model.notification.Notification;

import java.util.List;
import java.util.Map;

public class NotificationForDistributorListCommand implements Command<Map<String,List<Notification>>>  {

    private NotificationFacade notificationFacade = NotificationFacade.getInstance();


    @Override
    public <S> Map<String,List<Notification>>  execute(S param) {

        return notificationFacade.findAllNotification((Employee) param);
    }
}
