package server.message.chaingOfResposibility.notification;

import server.fasade.NotificationFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.message.MessageType;
import server.model.notification.Notification;

import java.util.List;
import java.util.Map;

public class SaveFirstNotificationElement extends AbstractChainElement {

    public SaveFirstNotificationElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) throws EndElementOfChainResponsibilityException {
        NotificationFacade facade = NotificationFacade.getInstance();
        Long id = facade.saveFirstNotification(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(id)
                .build();
        return message;
    }
}
