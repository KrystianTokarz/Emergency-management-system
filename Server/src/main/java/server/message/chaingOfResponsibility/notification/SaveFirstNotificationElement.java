package server.message.chaingOfResponsibility.notification;

import server.fasade.NotificationFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;
import server.model.message.MessageType;

/**
 * Chain of responsibility pattern element
 */
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
