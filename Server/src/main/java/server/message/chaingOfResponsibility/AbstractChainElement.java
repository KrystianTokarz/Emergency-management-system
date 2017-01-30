package server.message.chaingOfResponsibility;

import server.message.Message;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;
import server.model.message.MessageType;


/**
 * Abstract class for Chain of Responsibility pattern with Template Method pattern
 */
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
