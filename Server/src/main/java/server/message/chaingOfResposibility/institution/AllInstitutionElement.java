package server.message.chaingOfResposibility.institution;

import server.fasade.EmployeesFacade;
import server.fasade.InstitutionFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.institution.Institution;
import server.model.message.MessageType;

import java.util.List;

public class AllInstitutionElement extends AbstractChainElement {

    public AllInstitutionElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        InstitutionFacade facade = InstitutionFacade.getInstance();
        List<Institution> allInstitutions = facade.findAllInstitutions();
        Message message = new Message.MessageBuilder(messageType)
                .object(allInstitutions)
                .build();
        return message;
    }
}
