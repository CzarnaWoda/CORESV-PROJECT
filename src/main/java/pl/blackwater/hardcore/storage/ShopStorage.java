package pl.blackwater.hardcore.storage;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Shop;
import pl.blackwater.hardcore.managers.EnchantManager;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.HashMap;

@Getter
public class ShopStorage extends ConfigCreator {

    private HashMap<String,Shop> buyItems;
    private HashMap<String,Shop> sellItems;

    public ShopStorage() {
        super("shop.yml", "shopStorage", Core.getInstance());

        buyItems = loadBuyItems();
        sellItems = loadSellItems();
    }
    public HashMap<String, Shop> loadBuyItems(){
        final FileConfiguration configuration = getConfig();

        final HashMap<String, Shop> buyItems = new HashMap<>();
        for(String key : configuration.getConfigurationSection("buyitems").getKeys(false)){
            final ConfigurationSection section = configuration.getConfigurationSection("buyitems." + key);

            final int coins = section.getInt("coins");
            final ItemStack item = itemConvert(section.getString("item"));

            final Shop shop = new Shop(key,coins,item);
            buyItems.put(key, shop);
        }
        return buyItems;
    }
    public HashMap<String, Shop> loadSellItems(){
        final FileConfiguration configuration = getConfig();

        final HashMap<String, Shop> sellItems = new HashMap<>();
        for(String key : configuration.getConfigurationSection("sellitems").getKeys(false)){
            final ConfigurationSection section = configuration.getConfigurationSection("sellitems." + key);

            final int coins = section.getInt("coins");
            final ItemStack item = itemConvert(section.getString("item"));

            final Shop shop = new Shop(key,coins,item);
            sellItems.put(key, shop);
        }
        return sellItems;
    }
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
            itemStack.addUnsafeEnchantments(enchants);
        }
        return itemStack;
    }
}
