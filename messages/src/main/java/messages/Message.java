package messages;

import messageenum.MessageType;

public class Message {

    private MessageType messageType;
    private String result;

    public Message(MessageType messageType, String result){
        this.messageType = messageType;
        this.result = result;
    }

    public Message(){

    }

    public MessageType getMessageType() {
        return messageType;
    }
    public String getResult() {
        return result;
    }

}
