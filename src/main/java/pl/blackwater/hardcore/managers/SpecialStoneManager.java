package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;

import java.util.List;

public class SpecialStoneManager {
    @Getter private List<Location> specialStoneLocations;
    @Getter private List<ItemStack> specialStoneItems;

    public SpecialStoneManager(){
        specialStoneLocations = Core.getSpecialStoneStorage().loadLocations();
        specialStoneItems = Core.getSpecialStoneStorage().loadItems();
    }
    public void saveLocation(){
        Core.getSpecialStoneStorage().saveLocations(specialStoneLocations);
    }

}
