package server.message.chaingOfResponsibility.employees;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.message.MessageType;

import java.util.List;
import java.util.logging.Logger;

/**
 * Chain of responsibility pattern element
 */
public class EmployeeListElement extends AbstractChainElement {

    public EmployeeListElement(MessageType messageType){
        this.messageType = messageType;
    }
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Message execute(Object objectFromClient) {
        EmployeesFacade facade = EmployeesFacade.getInstance();
        List<Employee> employees = facade.findAllEmployeesForAdministrator();
        Message message = new Message.MessageBuilder(messageType)
                .object(employees)
                .build();
        return message;
    }
}
