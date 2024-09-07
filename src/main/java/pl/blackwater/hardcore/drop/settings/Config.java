package pl.blackwater.hardcore.drop.settings;

import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.List;

public class Config extends ConfigCreator {

	public static double DROP_VIP_DROP;
	public static int DROP_VIP_EXP;
	public static double DROP_TURBO_DROP;
	public static int DROP_TURBO_EXP;
	public static int DROP_TURBO_LOSOWANIE_ILOSC;
	public static int DROP_OBSIDIAN_AMOUNT;
	public static boolean COINS_STATUS;
	public static int COINS_MIN;
	public static int COINS_MAX;
	public static int EVENT_EXP;
	public static int EVENT_DROP;
	public static List<Integer> DROP_LVL_BROADCAST;
	public static String DROP_CHEST_NAME;
	public static long DROP_CHEST_TIME;

    public Config() {
        super("dropconfig.yml", "drop config", Core.getInstance());
        FileConfiguration config = getConfig();
        DROP_CHEST_TIME = config.getLong("drop.chest.time");
        DROP_VIP_DROP = config.getDouble("drop.vip.drop");
        DROP_VIP_EXP = config.getInt("drop.vip.exp");
        DROP_TURBO_DROP = config.getInt("drop.turbo.drop");
        DROP_TURBO_EXP = config.getInt("drop.turbo.exp");
        DROP_TURBO_LOSOWANIE_ILOSC = config.getInt("drop.turbo.losowanie.ilosc");
        COINS_STATUS = config.getBoolean("coins.status");
        COINS_MIN = config.getInt("coins.min");
        COINS_MAX = config.getInt("coins.max");
        EVENT_EXP = config.getInt("event.exp");
        EVENT_DROP = config.getInt("event.drop");
        DROP_OBSIDIAN_AMOUNT = config.getInt("drop.obsidian.amount");
        DROP_CHEST_NAME = config.getString("drop.chest.name");
        DROP_LVL_BROADCAST = config.getIntegerList("drop_lvl_broadcast");
    }

}
