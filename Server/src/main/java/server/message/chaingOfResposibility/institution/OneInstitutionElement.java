package server.message.chaingOfResposibility.institution;

import server.fasade.EmployeesFacade;
import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

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
