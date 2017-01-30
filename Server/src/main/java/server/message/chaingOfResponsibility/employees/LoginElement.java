package server.message.chaingOfResponsibility.employees;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResponsibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.message.MessageType;

/**
 * Chain of responsibility pattern element
 */
public class LoginElement extends AbstractChainElement{

    public LoginElement(MessageType messageType){
        this.messageType = messageType;
    }


    @Override
    public Message execute(Object objectFromClient) {

        EmployeesFacade facade = EmployeesFacade.getInstance();
        Employee employeeByLoginAndPassword = facade.findEmployeeByLoginAndPassword(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(employeeByLoginAndPassword)
                .build();
        return message;
    }
}
