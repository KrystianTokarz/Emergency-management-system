package server.message.chaingOfResposibility.notification;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResposibility.AbstractChainElement;
import server.model.employee.Employee;
import server.model.message.MessageType;

public class DistributorDataElement extends AbstractChainElement {

    public DistributorDataElement(MessageType messageType){
        this.messageType = messageType;
    }

    @Override
    public Message execute(Object objectFromClient) {
        EmployeesFacade facade = EmployeesFacade.getInstance();
        Employee employeeByFirstAndLastNameAndMail = facade.findEmployeeByFirstAndLastNameAndMail(objectFromClient);
        Message message = new Message.MessageBuilder(messageType)
                .object(employeeByFirstAndLastNameAndMail)
                .build();
        return message;
    }
}
