package server.message.chaingOfResposibility.notification;

import server.fasade.InstitutionFacade;
import server.fasade.LocalizationFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.institution.Institution;
import server.model.localization.Province;
import server.model.message.MessageType;

import java.util.List;

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
