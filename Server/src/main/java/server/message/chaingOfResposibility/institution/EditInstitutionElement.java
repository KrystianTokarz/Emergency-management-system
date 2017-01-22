package server.message.chaingOfResposibility.institution;

import server.fasade.EmployeesFacade;
import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

public class EditInstitutionElement extends AbstractChainElement {

    public EditInstitutionElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        InstitutionFacade facade = InstitutionFacade.getInstance();
        List<Institution> institutions = facade.updateInstitution(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(institutions)
                .build();
        return message;
    }
}
