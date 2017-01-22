package server.message.chaingOfResposibility;

import server.fasade.EmployeesFacade;
import server.message.Message;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;
import server.model.employee.Employee;
import server.model.message.MessageType;

public abstract class AbstractChainElement {


    protected AbstractChainElement nextElement;
    protected MessageType messageType;

    public void setNextElement(AbstractChainElement nextElement){
        this.nextElement = nextElement;
    }


    abstract public Message execute(Object object) throws EndElementOfChainResponsibilityException;


    //TEMPLATE METHOD
    public  Message  processRequest(Message message) throws EndElementOfChainResponsibilityException {
        Message messageToClient;
        if(message.getType() == messageType){
            messageToClient = this.execute(message.getObject());
        }else{
            messageToClient = nextElement.processRequest(message);
        }
        return messageToClient;
    }
}
