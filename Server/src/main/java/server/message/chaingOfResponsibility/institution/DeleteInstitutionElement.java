package server.message.chaingOfResponsibility.institution;

import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

/**
 * Chain of responsibility pattern element
 */
public class DeleteInstitutionElement extends AbstractChainElement {

    public DeleteInstitutionElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        InstitutionFacade facade = InstitutionFacade.getInstance();
        List<Institution> institutions = facade.deleteInstitution(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(institutions)
                .build();
        return message;
    }
}
