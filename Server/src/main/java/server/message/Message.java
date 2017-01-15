package server.message;

import server.model.message.MessageType;

import java.io.Serializable;


/**
 * Class which allow to send and receive different Object from/to client
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

            if((messageType == MessageType.SAVE_NEW_EMPLOYEE) || (messageType == MessageType.DELETE_EMPLOYEES || (messageType == MessageType.EDIT_EMPLOYEE)))
                messageType = MessageType.EMPLOYEE_LIST;
            if((messageType == MessageType.SAVE_NEW_INSTITUTION ) || (messageType == MessageType.DELETE_INSTITUTIONS ||  messageType == MessageType.EDIT_INSTITUTION))
                messageType = MessageType.ALL_INSTITUTION_LIST;
//


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
