package server.message.chaingOfResponsibility.institution;

import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.model.institution.Institution;
import server.model.message.MessageType;

/**
 * Chain of responsibility pattern element
 */
public class OneInstitutionElement extends AbstractChainElement {

    public OneInstitutionElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        InstitutionFacade facade = InstitutionFacade.getInstance();
        Institution institutionByName = facade.findInstitutionByName(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(institutionByName)
                .build();
        return message;
    }
}
