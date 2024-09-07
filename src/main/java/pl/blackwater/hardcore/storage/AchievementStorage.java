package pl.blackwater.hardcore.storage;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Achievement;
import pl.blackwater.hardcore.enums.AchievementType;
import pl.blackwater.hardcore.managers.EnchantManager;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class AchievementStorage extends ConfigCreator {

    private HashMap<String, Achievement> achviments;

    public AchievementStorage(){
        super("achievements.yml", "Achievements", Core.getInstance());
        achviments = loadAchievements();
    }



    public HashMap<String, Achievement> loadAchievements(){
        final FileConfiguration config = getConfig();
        final HashMap<String, Achievement> achievements = new HashMap<>();
        for(String s : config.getConfigurationSection("achievements").getKeys(false)){
            ConfigurationSection section = config.getConfigurationSection("achievements." + s);
            final String gui_itemname = section.getString("gui_itemname");
            final String gui_item = section.getString( "gui_item");
            final int gui_itemdata = section.getInt("gui_itemdata");
            final List<String> gui_itemlore = section.getStringList( "gui_itemlore");
            final int achievementAmount = section.getInt( "achievementAmount");
            final int rewardCoins = section.getInt( "rewardCoins");
            final String achievementType = section.getString("achievementType");
            final List<ItemStack> itemStacks = new ArrayList<>();
            for(String is : section.getStringList("rewardItems")){
                itemStacks.add(itemConvert(is));
            }
            achievements.put(s, new Achievement(s,gui_itemname, Material.getMaterial(gui_item),gui_itemdata,achievementAmount,rewardCoins,gui_itemlore, AchievementType.valueOf(achievementType), itemStacks));
        }
        return achievements;
    }

    //id-data-amount-enchant=enchantlevel;enchant=enchantlevel
    public ItemStack itemConvert(@NotNull String item){
        String[] array = item.split("-");
        final int id = Integer.parseInt(array[0]);
        final short data = Short.parseShort(array[1]);
        final int amount = Integer.parseInt(array[2]);
        final HashMap<Enchantment, Integer> enchants = new HashMap<>();
        ItemStack itemStack = new ItemStack(id,amount,data);
        if(array.length > 3) {
            for (String enchantments : array[3].split(";")) {
                String[] array2 = enchantments.split("=");
                if(array2.length > 1) {
                    enchants.put(EnchantManager.get(array2[0]), Integer.parseInt(array2[1]));
                }
            }
            itemStack.addUnsafeEnchantments(enchants);
        }
        return itemStack;
    }
}
