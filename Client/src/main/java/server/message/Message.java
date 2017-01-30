package server.message;

import server.model.message.MessageType;

import java.io.Serializable;

/**
 * Class which allow to send and receive different Object from/to server (Proxy pattern)
 */
public class Message implements Serializable {

    static final long serialVersionUID = 1L;

    private MessageType messageType;
    private Object object;

    public Message(MessageBuilder builder ){

        this.object = builder.object;
        this.messageType = builder.messageType;
    }

    public MessageType getType() {
        return messageType;
    }

    public Object getObject() {
        return object;
    }

    public static class MessageBuilder{
        private MessageType messageType;
        private Object object;

        public MessageBuilder(MessageType messageType){
            this.messageType=messageType;
        }

        public MessageBuilder object(Object object){
            this.object = object;
            return  this;
        }

        public Message build(){
            return new Message(this);
        }
    }
}
