package server.message.chaingOfResponsibility.notification;

import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

/**
 * Chain of responsibility pattern element
 */
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
