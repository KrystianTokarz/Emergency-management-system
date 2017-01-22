package server.message.chaingOfResposibility.employees;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.message.MessageType;

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
