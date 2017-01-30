package server.message.chaingOfResponsibility;

import server.message.Message;
import server.message.chaingOfResponsibility.exception.EndElementOfChainResponsibilityException;

/**
 * Chain of responsibility element which is the last in 'chain'
 */
public class EndElement extends AbstractChainElement {

    public EndElement(){
    }

    @Override
    public Message execute(Object object) throws EndElementOfChainResponsibilityException  {
        throw new EndElementOfChainResponsibilityException();
    }
}
