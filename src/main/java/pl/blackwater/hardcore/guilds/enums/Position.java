package pl.blackwater.hardcore.guilds.enums;

import lombok.Getter;

@Getter
public enum  Position {
    LIDER("lider"),PRELIDER("prelider"),MEMBER("member");

    private String type;

    Position(String type) {
        this.type = type;
    }

    public static Position getByName(String type){
        for(Position position : values()){
            if(position.getType().equalsIgnoreCase(type)){
                return position;
            }
        }
        return MEMBER;
    }
}
