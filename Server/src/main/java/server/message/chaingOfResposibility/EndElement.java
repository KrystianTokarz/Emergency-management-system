package server.message.chaingOfResposibility;

import server.message.Message;
import server.message.chaingOfResposibility.exception.EndElementOfChainResponsibilityException;

public class EndElement extends AbstractChainElement {

    public EndElement(){

    }





    @Override
    public Message execute(Object object) throws EndElementOfChainResponsibilityException  {
        System.out.println("NIE DOSZLO TUTAJ UFF");
        throw new EndElementOfChainResponsibilityException();
    }
}
