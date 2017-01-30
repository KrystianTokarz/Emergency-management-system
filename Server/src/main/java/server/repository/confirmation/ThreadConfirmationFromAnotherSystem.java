package server.repository.confirmation;

import server.model.message.SecondMessageWithNotification;
import server.repository.NotificationRepository;

/**
 * Additional thread for create confirmation notification from another systems
 */
public class ThreadConfirmationFromAnotherSystem extends Thread{

    private SecondMessageWithNotification secondMessageWithNotification;

    public ThreadConfirmationFromAnotherSystem(SecondMessageWithNotification secondMessageWithNotification){
        this.secondMessageWithNotification = secondMessageWithNotification;
    }
    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NotificationRepository repository = NotificationRepository.getInstance();
        repository.changeNotificationStatus(secondMessageWithNotification);
    }
}
