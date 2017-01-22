package server.message.chaingOfResposibility.notification;

import server.fasade.NotificationFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.message.MessageType;

public class SaveSecondNotificationElement extends AbstractChainElement {

    public SaveSecondNotificationElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) throws EndElementOfChainResponsibilityException {
        NotificationFacade facade = NotificationFacade.getInstance();
        Boolean result = facade.saveSecondNotification(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(result)
                .build();
        return message;
    }
}
