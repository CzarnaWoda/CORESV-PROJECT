package pl.blackwater.hardcore.storage;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.utils.LocationUtil;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

public class SpecialStoneStorage extends ConfigCreator {
    public SpecialStoneStorage() {
        super("specialstone.yml", "locations!", Core.getInstance());
    }
    public List<Location> loadLocations(){
        List<String> locations = getConfig().getStringList("specialstone.locations");
        List<Location> l = new ArrayList<>();
        if(locations != null)
            locations.forEach(s -> l.add(LocationUtil.convertStringToBlockLocation(s)));
        return l;
    }
    public List<ItemStack> loadItems(){
        return ItemUtil.getItems(getConfig().getString("specialstone.items"),1);
    }
    public void saveLocations(List<Location> locations){
        List<String> list = new ArrayList<>();
        locations.forEach(l -> list.add(LocationUtil.convertLocationBlockToString(l)));
        setField("specialstone.locations",list);
    }
}
