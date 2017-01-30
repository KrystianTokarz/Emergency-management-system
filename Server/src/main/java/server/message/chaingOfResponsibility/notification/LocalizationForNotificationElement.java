package server.message.chaingOfResponsibility.notification;

import server.fasade.LocalizationFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;
import server.model.localization.Province;
import server.model.message.MessageType;

import java.util.List;

/**
 * Chain of responsibility pattern element
 */
public class LocalizationForNotificationElement extends AbstractChainElement {

    public LocalizationForNotificationElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object object) throws EndElementOfChainResponsibilityException {
        LocalizationFacade facade = LocalizationFacade.getInstance();
        List<Province> allProvince = facade.findAllProvince();
        Message message = new Message.MessageBuilder(messageType)
                .object(allProvince)
                .build();
        return message;
    }
}
