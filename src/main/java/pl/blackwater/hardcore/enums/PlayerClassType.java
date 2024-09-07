package pl.blackwater.hardcore.enums;

import lombok.Getter;

@Getter
public enum PlayerClassType {
    WARRIOR("wojownik"),ARCHER("lucznik"),DIGGER("kopacz"),UNKNOWN("brak");

    private String name;
    PlayerClassType(String classname) {
        this.name = classname;
    }
    public static PlayerClassType getByName(String name){
        for(PlayerClassType type : values()){
            if(type.getName().equalsIgnoreCase(name)){
                return type;
            }
        }
        return null;
    }
}
