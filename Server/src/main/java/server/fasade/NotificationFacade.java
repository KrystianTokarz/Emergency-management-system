package server.fasade;

import server.model.employee.Employee;
import server.model.message.FirstMessageWithNotification;
import server.model.message.SecondMessageWithNotification;
import server.model.notification.Notification;
import server.repository.NotificationRepository;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Facade to support operation on Notifications
 */
public class NotificationFacade {

    private NotificationFacade() {
    }

    private static NotificationFacade instance = null;

    public static NotificationFacade getInstance() {
        if (instance == null) {
            instance = new NotificationFacade();
        }
        return instance;
    }

    private NotificationRepository notificationRepository = NotificationRepository.getInstance();

    public Map<String,List<Notification>> findAllNotification(Object employeeObject) {
        Employee employee = (Employee) employeeObject;
        return notificationRepository.findAllNotification(employee);
    }

    public Long saveFirstNotification(Object messageWithNotificationObject) {
        FirstMessageWithNotification messageWithNotification = (FirstMessageWithNotification) messageWithNotificationObject;
        return notificationRepository.saveFirstNotification(messageWithNotification);
    }

    public Boolean saveSecondNotification(Object messageWithNotificationObject) {
        SecondMessageWithNotification messageWithNotification = (SecondMessageWithNotification) messageWithNotificationObject;
        return notificationRepository.saveSecondNotification(messageWithNotification);
    }
}
