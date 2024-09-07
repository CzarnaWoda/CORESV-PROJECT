package pl.blackwater.hardcore.settings;

import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.List;

public class CoreConfig extends ConfigCreator {

    public static int DURINGFIGHT_BLOCKPLACE_Y,CHATMANAGER_SLOWMODE,CHATMANAGER_POINTS,COMBATMANAGER_ESCAPETIME,SLOTMANAGER_SLOTS,ANTYMACROMANAGER_CPSAMOUNT,DEPOSITMANAGER_LIMITKOX,DEPOSITMANAGER_LIMITPEARL,DEPOSITMANAGER_LIMITREF;
    public static List<String> COMBATMANAGER_BLOCKEDCMD;
    public static String CHATMANAGER_FORMAT_GLOBAL,CHATMANAGER_FORMAT_AGLOBAL,CHATMANAGER_FORMAT_TAG;
    public static long ENABLE_DIAMONDITEMS;
    public static boolean DURINGFIGHT_BLOCKPLACE_ENABLE;
    public static int BORDERMANAGER_BORDER_X,BORDERMANAGER_BORDER_Z,BORDERMANAGER_BORDER_WORLDRADIUS,BORDERMANAGER_BORDER_NETHERRADIUS;

    public CoreConfig() {
        super("coreconfig.yml", "Core config", Core.getInstance());

        FileConfiguration config = getConfig();

        CHATMANAGER_SLOWMODE = config.getInt("chatmanager.slowmode");
        CHATMANAGER_POINTS = config.getInt("chatmanager.points");
        CHATMANAGER_FORMAT_GLOBAL = config.getString("chatmanager.format.global");
        CHATMANAGER_FORMAT_AGLOBAL = config.getString("chatmanager.format.aglobal");
        COMBATMANAGER_ESCAPETIME = config.getInt("combatmanager.escapetime");
        COMBATMANAGER_BLOCKEDCMD = config.getStringList("combatmanager.blockedcmd");
        SLOTMANAGER_SLOTS = config.getInt("slotmanager.slots");
        ENABLE_DIAMONDITEMS = config.getLong("enable.diamonditems");
        DEPOSITMANAGER_LIMITKOX = config.getInt("depositmanager.limitkox");
        DEPOSITMANAGER_LIMITPEARL = config.getInt("depositmanager.limitpearl");
        DEPOSITMANAGER_LIMITREF = config.getInt("depositmanager.limitref");
        ANTYMACROMANAGER_CPSAMOUNT = config.getInt("antymacro.cpsamount");
        DURINGFIGHT_BLOCKPLACE_ENABLE = config.getBoolean("duringfight.blockplace.enable");
        DURINGFIGHT_BLOCKPLACE_Y = config.getInt("duringfight.blockplace.y");
        CHATMANAGER_FORMAT_TAG = config.getString("chatmanager.format.tag");
        BORDERMANAGER_BORDER_X = config.getInt("bordermanager.border.x");
        BORDERMANAGER_BORDER_Z = config.getInt("bordermanager.border.z");
        BORDERMANAGER_BORDER_WORLDRADIUS = config.getInt("bordermanager.border.worldradius");
        BORDERMANAGER_BORDER_NETHERRADIUS = config.getInt("bordermanager.border.netherradius");

    }
}
