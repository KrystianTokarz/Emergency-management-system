package server.message.chaingOfResponsibility.employees;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.message.MessageType;

import java.util.List;

/**
 * Chain of responsibility pattern element
 */
public class EditEmployeeElement extends AbstractChainElement {

    public EditEmployeeElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        EmployeesFacade facade = EmployeesFacade.getInstance();
        List<Employee> employees = facade.updateEmployee(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(employees)
                .build();
        return message;
    }
}
