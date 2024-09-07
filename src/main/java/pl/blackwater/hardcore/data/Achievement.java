package pl.blackwater.hardcore.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.enums.AchievementType;

import java.util.List;

@Data
public class Achievement {

    @NonNull public String name,gui_itemname;
    @NonNull public Material gui_item;
    @NonNull public int gui_itemdata;
    @NonNull public int achievementAmount;
    @NonNull public int rewardCoins;
    @NonNull public List<String> gui_itemlore;
    @NonNull public AchievementType achievementType;
    @NonNull public List<ItemStack> rewardItems;

}
