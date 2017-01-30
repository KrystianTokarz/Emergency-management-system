package server.message.chaingOfResponsibility.exception;

import java.io.IOException;

/**
 * Exception for chain of responsibility pattern which is thrown when neither elements can't support message from client
 */
public class EndElementOfChainResponsibilityException extends IOException{
}
