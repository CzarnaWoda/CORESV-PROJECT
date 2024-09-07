package pl.blackwater.hardcore.managers;

import lombok.Getter;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.settings.CoreConfig;

public class EnableManager {

    @Getter private long diamondItems = CoreConfig.ENABLE_DIAMONDITEMS;

    public boolean isDiamondItems(){
        return System.currentTimeMillis()>diamondItems;
    }

    public void setDiamondItems(long l){
        diamondItems = l;
        Core.getCoreConfig().getConfig().set("enable.diamonditems", diamondItems);
        Core.getCoreConfig().saveConfig();
    }
}
