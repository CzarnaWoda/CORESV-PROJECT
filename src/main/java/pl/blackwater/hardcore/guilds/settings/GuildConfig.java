package pl.blackwater.hardcore.guilds.settings;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.utils.ItemUtil;

import java.util.List;

public class GuildConfig extends ConfigCreator {

    public static World CUBOID_WORLD;
    public static boolean CUBOID_SPAWN_ENABLED,GUILD_ITEMS_COINS_ENABLED;
    public static List<ItemStack> GUILD_ITEMS_DEFAULT,GUILD_ITEMS_VIP;
    public static String GUILD_ITEMS_JOIN;
    public static int GUILD_MEMBERS_LIMIT_MAX,GUILD_MEMBERS_LIMIT_START,CUBOID_SIZE_ADD, CUBOID_SIZE_MAX,CUBOID_SPAWN_DISTANCE,CUBOID_SIZE_BETWEEN,GUILD_ITEMS_COINS_AMOUNT,CUBOID_SIZE_START,GUILD_ITEMS_JOINMULTIPLER,GUILD_UPGRADES_EXPIRETIME,GUILD_UPGRADES_LIVES,GUILD_UPGRADES_CUBOID,GUILD_UPGRADES_PLAYERLIMIT;
    public static String SCOREBOARDMANAGER_ROOT,SCOREBOARDMANAGER_ADMIN,SCOREBOARDMANAGER_MOD,SCOREBOARDMANAGER_HELPER,SCOREBOARDMANAGER_VIP;
    public GuildConfig() {
        super("guildconfig.yml", "Guild Configuration", Core.getInstance());
        FileConfiguration config = getConfig();
        CUBOID_SIZE_ADD = config.getInt("cuboid.size.add");
        CUBOID_SIZE_MAX = config.getInt("cuboid.size.max");
        CUBOID_SIZE_BETWEEN = config.getInt("cuboid.spawn.between");
        CUBOID_SIZE_START = config.getInt("cuboid.size.start");
        CUBOID_WORLD = Bukkit.getWorld(config.getString("cuboid.spawn.world"));
        CUBOID_SPAWN_ENABLED = config.getBoolean("cuboid.spawn.enabled");
        CUBOID_SPAWN_DISTANCE = config.getInt("cuboid.spawn.distance");
        GUILD_ITEMS_DEFAULT = ItemUtil.getItems(config.getString("guild.items.default"),1);
        GUILD_ITEMS_VIP = ItemUtil.getItems(config.getString("guild.items.vip"),1);
        GUILD_ITEMS_JOIN = config.getString("guild.items.join");
        GUILD_ITEMS_COINS_ENABLED = config.getBoolean("guild.items.coins.enabled");
        GUILD_ITEMS_COINS_AMOUNT = config.getInt("guild.items.coins.amount");
        GUILD_UPGRADES_EXPIRETIME = config.getInt("guild.upgrades.expiretime");
        GUILD_UPGRADES_LIVES = config.getInt("guild.upgrades.lives");
        GUILD_UPGRADES_CUBOID = config.getInt("guild.upgrades.cuboid");
        GUILD_UPGRADES_PLAYERLIMIT = config.getInt("guild.upgrades.playerlimit");
        GUILD_MEMBERS_LIMIT_START =  config.getInt("guild.members.limit.start");
        GUILD_MEMBERS_LIMIT_MAX =  config.getInt("guild.members.limit.max");
        SCOREBOARDMANAGER_ROOT =  config.getString("scoreboard.root");
        SCOREBOARDMANAGER_ADMIN =  config.getString("scoreboard.admin");
        SCOREBOARDMANAGER_MOD =  config.getString("scoreboard.mod");
        SCOREBOARDMANAGER_HELPER =  config.getString("scoreboard.helper");
        SCOREBOARDMANAGER_VIP =  config.getString("scoreboard.vip");


    }
}
