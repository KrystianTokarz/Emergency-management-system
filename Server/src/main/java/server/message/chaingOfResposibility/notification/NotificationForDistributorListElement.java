package server.message.chaingOfResposibility.notification;

import server.fasade.InstitutionFacade;
import server.fasade.NotificationFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.institution.Institution;
import server.model.message.MessageType;
import server.model.notification.Notification;

import java.util.List;
import java.util.Map;

public class NotificationForDistributorListElement extends AbstractChainElement {

    public NotificationForDistributorListElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) throws EndElementOfChainResponsibilityException {
        NotificationFacade facade = NotificationFacade.getInstance();
        Map<String, List<Notification>> allNotification = facade.findAllNotification(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(allNotification)
                .build();
        return message;
    }
}
