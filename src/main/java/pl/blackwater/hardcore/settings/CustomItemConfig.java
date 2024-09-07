package pl.blackwater.hardcore.settings;

import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.List;

public class CustomItemConfig extends ConfigCreator {

    public static String ITEMS_STONIARKA_ITEM, ITEMS_STONIARKA_NAME,ITEMS_STONIARKA_SIMPLECRAFTING;
    public static List<String> ITEMS_STONIARKA_LORE;
    public static List<Integer> ITEMS_STONIARKA_CRAFTING;
    public static boolean ITEMS_STONIARKA_ENABLE;

    public static String ITEMS_SANDFARMER_ITEM, ITEMS_SANDFARMER_NAME,ITEMS_SANDFARMER_SIMPLECRAFTING;
    public static List<String> ITEMS_SANDFARMER_LORE;
    public static List<Integer> ITEMS_SANDFARMER_CRAFTING;
    public static boolean ITEMS_SANDFARMER_ENABLE;

    public static String ITEMS_BOYFARMER_ITEM, ITEMS_BOYFARMER_NAME,ITEMS_BOYFARMER_SIMPLECRAFTING;
    public static List<String> ITEMS_BOYFARMER_LORE;
    public static List<Integer> ITEMS_BOYFARMER_CRAFTING;
    public static boolean ITEMS_BOYFARMER_ENABLE;

    public static String ITEMS_AIRGENERATOR_ITEM, ITEMS_AIRGENERATOR_NAME,ITEMS_AIRGENERATOR_SIMPLECRAFTING;
    public static List<String> ITEMS_AIRGENERATOR_LORE;
    public static List<Integer> ITEMS_AIRGENERATOR_CRAFTING;
    public static boolean ITEMS_AIRGENERATOR_ENABLE;

    public static String ITEMS_SPECIALSTONE_ITEM, ITEMS_SPECIALSTONE_NAME,ITEMS_SPECIALSTONE_SIMPLECRAFTING;
    public static List<String> ITEMS_SPECIALSTONE_LORE;
    public static List<Integer> ITEMS_SPECIALSTONE_CRAFTING;
    public static boolean ITEMS_SPECIALSTONE_ENABLE;

    public static String ITEMS_GOLDENAPPLE_ITEM, ITEMS_GOLDENAPPLE_NAME,ITEMS_GOLDENAPPLE_SIMPLECRAFTING;
    public static List<String> ITEMS_GOLDENAPPLE_LORE;
    public static List<Integer> ITEMS_GOLDENAPPLE_CRAFTING;
    public static boolean ITEMS_GOLDENAPPLE_ENABLE;

    public static String ITEMS_MYSTERYBOX_ITEM, ITEMS_MYSTERYBOX_NAME,ITEMS_MYSTERYBOX_SIMPLECRAFTING;
    public static List<String> ITEMS_MYSTERYBOX_LORE;
    public static List<Integer> ITEMS_MYSTERYBOX_CRAFTING;
    public static boolean ITEMS_MYSTERYBOX_ENABLE;
    
    public CustomItemConfig() {
        super("items.yml", "CustomItemConfig", Core.getInstance());
        FileConfiguration config = getConfig();
        ITEMS_STONIARKA_ITEM = config.getString("items.stoniarka.item");
        ITEMS_STONIARKA_NAME = config.getString("items.stoniarka.name");
        ITEMS_STONIARKA_SIMPLECRAFTING = config.getString("items.stoniarka.simplecrafting");
        ITEMS_STONIARKA_LORE = config.getStringList("items.stoniarka.lore");
        ITEMS_STONIARKA_CRAFTING = config.getIntegerList("items.stoniarka.crafting");
        ITEMS_STONIARKA_ENABLE = config.getBoolean("items.stoniarka.enable");

        ITEMS_SANDFARMER_ITEM = config.getString("items.sandfarmer.item");
        ITEMS_SANDFARMER_NAME = config.getString("items.sandfarmer.name");
        ITEMS_SANDFARMER_SIMPLECRAFTING = config.getString("items.sandfarmer.simplecrafting");
        ITEMS_SANDFARMER_LORE = config.getStringList("items.sandfarmer.lore");
        ITEMS_SANDFARMER_CRAFTING = config.getIntegerList("items.sandfarmer.crafting");
        ITEMS_SANDFARMER_ENABLE = config.getBoolean("items.sandfarmer.enable");

        ITEMS_BOYFARMER_ITEM = config.getString("items.boyfarmer.item");
        ITEMS_BOYFARMER_NAME = config.getString("items.boyfarmer.name");
        ITEMS_BOYFARMER_SIMPLECRAFTING = config.getString("items.boyfarmer.simplecrafting");
        ITEMS_BOYFARMER_LORE = config.getStringList("items.boyfarmer.lore");
        ITEMS_BOYFARMER_CRAFTING = config.getIntegerList("items.boyfarmer.crafting");
        ITEMS_BOYFARMER_ENABLE = config.getBoolean("items.boyfarmer.enable");

        ITEMS_AIRGENERATOR_ITEM = config.getString("items.airgenerator.item");
        ITEMS_AIRGENERATOR_NAME = config.getString("items.airgenerator.name");
        ITEMS_AIRGENERATOR_SIMPLECRAFTING = config.getString("items.airgenerator.simplecrafting");
        ITEMS_AIRGENERATOR_LORE = config.getStringList("items.airgenerator.lore");
        ITEMS_AIRGENERATOR_CRAFTING = config.getIntegerList("items.airgenerator.crafting");
        ITEMS_AIRGENERATOR_ENABLE = config.getBoolean("items.airgenerator.enable");

        ITEMS_SPECIALSTONE_ITEM = config.getString("items.specialstone.item");
        ITEMS_SPECIALSTONE_NAME = config.getString("items.specialstone.name");
        ITEMS_SPECIALSTONE_SIMPLECRAFTING = config.getString("items.specialstone.simplecrafting");
        ITEMS_SPECIALSTONE_LORE = config.getStringList("items.specialstone.lore");
        ITEMS_SPECIALSTONE_CRAFTING = config.getIntegerList("items.specialstone.crafting");
        ITEMS_SPECIALSTONE_ENABLE = config.getBoolean("items.specialstone.enable");

        ITEMS_GOLDENAPPLE_ITEM = config.getString("items.goldenapple.item");
        ITEMS_GOLDENAPPLE_NAME = config.getString("items.goldenapple.name");
        ITEMS_GOLDENAPPLE_SIMPLECRAFTING = config.getString("items.goldenapple.simplecrafting");
        ITEMS_GOLDENAPPLE_LORE = config.getStringList("items.goldenapple.lore");
        ITEMS_GOLDENAPPLE_CRAFTING = config.getIntegerList("items.goldenapple.crafting");
        ITEMS_GOLDENAPPLE_ENABLE = config.getBoolean("items.goldenapple.enable");

        ITEMS_MYSTERYBOX_ITEM = config.getString("items.mysterybox.item");
        ITEMS_MYSTERYBOX_NAME = config.getString("items.mysterybox.name");
        ITEMS_MYSTERYBOX_SIMPLECRAFTING = config.getString("items.mysterybox.simplecrafting");
        ITEMS_MYSTERYBOX_LORE = config.getStringList("items.mysterybox.lore");
        ITEMS_MYSTERYBOX_CRAFTING = config.getIntegerList("items.mysterybox.crafting");
        ITEMS_MYSTERYBOX_ENABLE = config.getBoolean("items.mysterybox.enable");
    }
}
