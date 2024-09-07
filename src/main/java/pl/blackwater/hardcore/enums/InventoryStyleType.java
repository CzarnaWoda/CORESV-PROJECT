package pl.blackwater.hardcore.enums;

import lombok.Getter;

public enum InventoryStyleType {
    MCKOX("mckox"),CORESV("coresv");

    @Getter
    private String type;
    InventoryStyleType(String type) {
        this.type=type;
    }
}
