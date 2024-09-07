package pl.blackwater.hardcore.storage;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysteryStorage extends ConfigCreator {

    @Getter
    public static List<ItemStack> mysteryDrops;

    public static String MYSTERY_DROPMESSAGE;

    public MysteryStorage() {
        super("mysteryconfig.yml", "Mystery Boxes config", Core.getInstance());
        FileConfiguration config = getConfig();
        MYSTERY_DROPMESSAGE = config.getString("mystery.dropmessage");
        mysteryDrops = new ArrayList<>();
    }

    public void loadMysteryBox(){
        FileConfiguration config = getConfig();
        for(String item : config.getStringList("mystery.storage")){
            ItemStack is = itemConvert(item);
            mysteryDrops.add(is);
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
                    enchants.put(Enchantment.getByName(array2[0]), Integer.parseInt(array2[1]));
                }
            }
            itemStack.addUnsafeEnchantments(enchants);
        }
        return itemStack;
    }

    public String stringConvert(@NotNull ItemStack item){
        final int id = item.getTypeId();
        final short data = item.getDurability();
        final int amount = item.getAmount();
        StringBuilder convert = new StringBuilder(id + "-" + data + "-" + amount);
        if(item.getEnchantments() != null && item.getEnchantments().size() != 0){
            convert.append("-");
            for(Enchantment enchantment : item.getEnchantments().keySet()){
                convert.append(enchantment.getName()).append("=").append(item.getEnchantments().get(enchantment)).append(";");
            }
        }
        return convert.toString();
    }
}
