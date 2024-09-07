package pl.blackwater.hardcore.storage;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Kit;
import pl.blackwater.hardcore.managers.EnchantManager;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitsStorage extends ConfigCreator {
    public KitsStorage() {
        super("kits.yml", "Kit Config", Core.getInstance());
    }

    public void loadKits(){
        FileConfiguration config = getConfig();

        for(String key : config.getConfigurationSection("kits").getKeys(false)){
            ConfigurationSection section = config.getConfigurationSection("kits." + key);

            final List<ItemStack> items = new ArrayList<>();
            for(String s : section.getStringList("items")){
                items.add(itemConvert(s));
            }
            final int seconds = section.getInt("time");
            final boolean enable = section.getBoolean("enable");
            final int timekey = section.getInt("timekey");
            final Material menuitem = Material.getMaterial(section.getString("menuitem"));

            Core.getKitManager().getKits().put(key, new Kit(key, items, seconds, enable, timekey,menuitem));
        }
    }
    //id-data-amount-enchant=enchantlevel;enchant=enchantlevel
    public ItemStack itemConvert(@NotNull String item){
        String[] array = item.split("-");
        final int id = Integer.parseInt(array[0]);
        final short data = Short.parseShort(array[1]);
        final int amount = Integer.parseInt(array[2]);
        ItemStack itemStack = new ItemStack(id,amount,data);
        if(array.length > 3) {
            final HashMap<Enchantment, Integer> enchants = new HashMap<>();
            for (String enchantments : array[3].split(";")) {
                String[] array2 = enchantments.split("=");
                if(array2.length > 1) {
                    enchants.put(EnchantManager.get(array2[0]), Integer.parseInt(array2[1]));
                }
            }
            itemStack.addEnchantments(enchants);
        }
        return itemStack;
    }
}
