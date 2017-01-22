package server.message.chaingOfResposibility.notification;

import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

public class InstitutionForNotificationElement extends AbstractChainElement {

    public InstitutionForNotificationElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object object) throws EndElementOfChainResponsibilityException {
        InstitutionFacade facade = InstitutionFacade.getInstance();
        List<Institution> allInstitutions = facade.findAllInstitutions();
        Message message = new Message.MessageBuilder(messageType)
                .object(allInstitutions)
                .build();
        return message;
    }
}
