package pl.blackwater.hardcore.enums;

import lombok.Getter;

@Getter
public enum  MessageType {
    KILL("zabojstwo"),DROP("drop")
    ;
    private String desc;

    MessageType(String desc) {
        this.desc =  desc;
    }
    public static MessageType getMessageType(String desc){
        for(MessageType messageType : values()){
            if(messageType.getDesc().equalsIgnoreCase(desc)){
                return messageType;
            }
        }
        return null;
    }
}
