package server.fasade;

import server.model.employee.Employee;
import server.model.message.MessageWithNotification;
import server.model.notification.Notification;
import server.repository.EmployeeRepository;
import server.repository.NotificationRepository;

import java.util.List;
import java.util.Map;

/**
 * Facade to support operation on Notifications
 */
public class NotificationFacade {

    NotificationRepository notificationRepository = NotificationRepository.getInstance();



    public Map<String,List<Notification>> findAllNotification(Employee employee) {
        return notificationRepository.findAllNotification(employee);
    }

    public Long saveFirstNotification(MessageWithNotification messageWithNotification) {
        return notificationRepository.saveFirstNotification(messageWithNotification);
    }
}
