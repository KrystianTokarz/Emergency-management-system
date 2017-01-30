package server.message.chaingOfResponsibility.notification;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;
import server.model.employee.Employee;
import server.model.message.MessageType;

/**
 * Chain of responsibility pattern element
 */
public class EditEmployeeForDistributorElement extends AbstractChainElement {

    public EditEmployeeForDistributorElement(MessageType messageType){
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
