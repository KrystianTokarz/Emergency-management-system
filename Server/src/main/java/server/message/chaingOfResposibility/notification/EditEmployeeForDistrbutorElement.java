package server.message.chaingOfResposibility.notification;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.employee.Employee;
import server.model.message.MessageType;

import java.util.List;

public class EditEmployeeForDistrbutorElement extends AbstractChainElement {

    public EditEmployeeForDistrbutorElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) throws EndElementOfChainResponsibilityException {
        EmployeesFacade facade = EmployeesFacade.getInstance();
        Employee employee = facade.updateEmployeeFromDistributor(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(employee)
                .build();
        return message;
    }
}
