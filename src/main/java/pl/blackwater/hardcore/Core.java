package pl.blackwater.hardcore;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.blackwater.hardcore.commands.*;
import pl.blackwater.hardcore.commands.ranks.RankCommand;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.data.TextCommand;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.listeners.DropBlockBreakListener;
import pl.blackwater.hardcore.drop.listeners.GeneratorListener;
import pl.blackwater.hardcore.drop.managers.DropFile;
import pl.blackwater.hardcore.drop.managers.DropManager;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.enums.BackupType;
import pl.blackwater.hardcore.enums.InventoryStyleType;
import pl.blackwater.hardcore.enums.TabListType;
import pl.blackwater.hardcore.guilds.commands.GuildCommand;
import pl.blackwater.hardcore.guilds.listeners.*;
import pl.blackwater.hardcore.guilds.managers.AllianceManager;
import pl.blackwater.hardcore.guilds.managers.GuildManager;
import pl.blackwater.hardcore.guilds.managers.MemberManager;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.inventories.*;
import pl.blackwater.hardcore.listeners.*;
import pl.blackwater.hardcore.managers.*;
import pl.blackwater.hardcore.settings.*;
import pl.blackwater.hardcore.storage.*;
import pl.blackwater.hardcore.tasks.*;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwater.hardcore.utils.DiscoUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

public class Core extends JavaPlugin {

    @Getter
    private static Core instance;
    @Getter
    private static RankManager rankManager;
    @Getter
    @Setter
    private static RankStorage rankStorage;
    @Getter
    private static UserManager userManager;
    @Getter
    private static ChatManager chatManager;
    @Getter
    private static EasyScoreboardManager easyScoreboardManager;
    @Getter
    @Setter
    private static KitManager kitManager;
    @Getter
    @Setter
    private static KitsStorage kitsStorage;
    @Getter
    @Setter
    private static TextCommandManager textCommandManager;
    @Getter
    @Setter
    private static TextCommandsStorage textCommandsStorage;
    @Getter
    private static AntyMacroManager antyMacroManager;
    @Getter
    private static BackupManager backupManager;
    @Getter
    private static CustomItemManager customItemManager;
    @Getter
    private static CustomCraftingInventory customCraftingInventory;
    @Getter
    private static EnableManager enableManager;
    @Getter
    @Setter
    private static DropInventory dropInventory;
    @Getter
    @Setter
    private static Config dropConfig;
    @Getter
    @Setter
    private static SpecialStoneStorage specialStoneStorage;
    @Getter
    @Setter
    private static SpecialStoneManager specialStoneManager;
    @Getter
    @Setter
    private static CoreConfig coreConfig;
    @Getter
    @Setter
    private static MysteryStorage mysteryStorage;
    @Getter
    @Setter
    private static ShopStorage shopStorage;
    @Getter
    @Setter
    private static ShopInventory shopInventory;
    @Getter
    @Setter
    private static EffectStorage effectStorage;
    @Getter
    @Setter
    private static GuildConfig guildConfig;
    @Getter
    private static MemberManager memberManager;
    @Getter
    private static GuildManager guildManager;
    @Getter
    private static AllianceManager allianceManager;
    @Getter
    @Setter
    private static AchievementStorage achievementStorage;
    @Getter
    private static PlayerClassInventory playerClassInventory;
    @Getter
    private static TopInventory topInventory;
    @Getter
    private static ServerManagerInventory serverManagerInventory;
    @Override
    public void onLoad(){
        instance = this;
    }
    @Override
    public void onEnable(){
        final long start = System.currentTimeMillis();
        CoreUtil.sendConsoleMessage("Enable ProjectHardcore Core...");

        CoreUtil.sendConsoleMessage("Create MYSQL tables...");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}users` (" + "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," + " `uuid` varchar(255) NOT NULL, `lastName` varchar(16) NOT NULL, `firstIP` varchar(20) NOT NULL, `lastIP` varchar(20) NOT NULL, `lastKill` varchar(16) NOT NULL, `lastJoin` bigint(20)  NOT NULL, `lastKillTime` bigint(20) NOT NULL, `kills` int(5), `deaths` int(5) NOT NULL, `logouts` int(3) NOT NULL, `assists` int(5) NOT NULL, `points` int(5) NOT NULL, `timePlay` int(6) NOT NULL, `depositKox` int(5) NOT NULL, `depositRef` int(5) NOT NULL, `depositPearl` int(5) NOT NULL, `fly` int(1) NOT NULL, `god` int(1) NOT NULL, `gameMode` int(1) NOT NULL, `homeLocation` text NOT NULL, `lastLocation` text NOT NULL, `kitTimes` text NOT NULL, `drops` text NOT NULL, `rank` varchar(16) NOT NULL, `turboDropTime` bigint(20) NOT NULL, `turboExpTime` bigint(20) NOT NULL, `turboDrop` tinyint(1) NOT NULL, `turboExp` tinyint(1) NOT NULL, `bonusDrop` float(24) NOT NULL, `coins` int(11) NOT NULL, `exp` float(24) NOT NULL, `level` int(11) NOT NULL, `eatedKox` int(11) NOT NULL, `eatedRef` int(11) NOT NULL, `throwedPearl` int(11) NOT NULL, `enderChest` text NOT NULL, `takenAchievements` text NOT NULL, `openedChests` int(11) NOT NULL, `playerClass` varchar(10) NOT NULL, `messages` text NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}bans` (" +  "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"  + " `uuid` varchar(255) NOT NULL,`reason` varchar(255) NOT NULL,`admin` varchar(255) NOT NULL,`createtime` bigint(22) NOT NULL,`expiretime` bigint(22) NOT NULL,`unban` int(1) NOT NULL NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}ipbans` (" + "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"  + " `ip` varchar(32) NOT NULL,`reason` varchar(255) NOT NULL,`admin` varchar(255) NOT NULL,`createtime` bigint(22) NOT NULL,`expiretime` bigint(22) NOT NULL,`unban` int(1) NOT NULL NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}whitelisted` (" + "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," + " `uuid` varchar(255) NOT NULL NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}backups` (" +  "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"+ " `backupUUID` varchar(255) NOT NULL, `owner` varchar(255) NOT NULL, `armor` text NOT NULL, `inventory` text NOT NULL, `backupTime` bigint(22) NOT NULL, `takeBackupTime` bigint(22) NOT NULL, `backupType` varchar(12) NOT NULL, `backupCreator` varchar(16) NOT NULL NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}guilds` (" + "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"  +" `name` varchar(32) NOT NULL, `tag` varchar(4) NOT NULL, `owner` varchar(255) NOT NULL, `pvp` tinyint(1) NOT NULL, `createTime` bigint(64) NOT NULL, `expireTime` bigint(64) NOT NULL, `liveCool` bigint(64) NOT NULL, `playerLimits` int(11) NOT NULL, `lives` int(11) NOT NULL, `homeLocation` varchar(255) NOT NULL, `cuboidWorld` varchar(255) NOT NULL, `cuboidX` int(11) NOT NULL, `cuboidZ` int(11) NOT NULL, `cuboidSize` int(11) NOT NULL, `deposit` int(18) NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}members` (" +  "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"+ " `uuid` varchar(255) NOT NULL NOT NULL, `guild` text NOT NULL, `position` text NOT NULL, `permissions` text NOT NULL);");
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}alliances` (" +  "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"  + " `guild1` text NOT NULL, `guild2` text NOT NULL);");
        CoreUtil.sendConsoleMessage("MYSQL tables created");

        CoreUtil.sendConsoleMessage("Implements managers...");
        rankManager = new RankManager();
        userManager = new UserManager();
        chatManager = new ChatManager();
        easyScoreboardManager = new EasyScoreboardManager();
        kitManager = new KitManager();
        textCommandManager = new TextCommandManager();
        antyMacroManager = new AntyMacroManager();
        backupManager = new BackupManager();
        enableManager = new EnableManager();
        API.registerConfig(new CustomItemConfig());
        customItemManager = new CustomItemManager();
        customCraftingInventory = new CustomCraftingInventory();
        DropFile.saveDefaultConfig();
        API.registerConfig(new Config());
        dropConfig = new Config();
        dropInventory = new DropInventory(InventoryStyleType.MCKOX);
        new BackupInventory();
        DropManager.setup();
        guildManager = new GuildManager();
        memberManager = new MemberManager();
        allianceManager = new AllianceManager();
        playerClassInventory = new PlayerClassInventory();
        topInventory = new TopInventory();
        CoreUtil.sendConsoleMessage("Managers implemented");

        CoreUtil.sendConsoleMessage("Implements configs...");
        API.registerConfig(new RankStorage());
        API.registerConfig(new MessageConfig());
        API.registerConfig(coreConfig = new CoreConfig());
        API.registerConfig(new KitsStorage());
        API.registerConfig(new AchievementStorage());
        API.registerConfig(new TextCommandsStorage());
        API.registerConfig(new BackupConfig());
        API.registerConfig(new SpecialStoneStorage());
        API.registerConfig(mysteryStorage = new MysteryStorage());
        API.registerConfig(shopStorage = new ShopStorage());
        API.registerConfig(effectStorage = new EffectStorage());
        API.registerConfig(guildConfig = new GuildConfig());
        API.registerConfig(new TabListConfig());
        CoreUtil.sendConsoleMessage("Configs implemented");

        CoreUtil.sendConsoleMessage("Implements storages...");
        rankStorage = new RankStorage();
        kitsStorage = new KitsStorage();
        textCommandsStorage = new TextCommandsStorage();
        specialStoneStorage = new SpecialStoneStorage();
        achievementStorage = new AchievementStorage();
        CoreUtil.sendConsoleMessage("Storages implemented");

        CoreUtil.sendConsoleMessage("Load data...");
        getRankStorage().loadRanks();
        getKitsStorage().loadKits();
        getTextCommandsStorage().loadTextCommands();
        getUserManager().load();
        BanManager.setup();
        BanIPManager.setup();
        WhiteListManager.setup();
        getBackupManager().load();
        specialStoneManager = new SpecialStoneManager();
        getMysteryStorage().loadMysteryBox();
        shopInventory = new ShopInventory(InventoryStyleType.CORESV);
        getGuildManager().load();
        getMemberManager().load();
        getAllianceManager().load();
        CoreUtil.sendConsoleMessage("Data loaded");

        CoreUtil.sendConsoleMessage("Register listeners...");
        API.registerListener(Core.getInstance(),new GuildActionListener(),new PlayerClassListener(),new BorderListener(),new PlayerInteractListener(),new RandomTeleportListener(),new GuildChatListener(),new GuildExplodeListener(),new PlayerDeathListener(),new PlayerMoveListener(),new PlayerCloseOpenInventoryListener(), new DropBlockBreakListener(),new CraftItemListener(), new GeneratorListener(),new AsyncPlayerChatListener(),new AntyMacroListener(),new PlayerJoinQuitListener(), new EntityDamageByEntityListener(), new GodListener(), new PlayerCommandPreprocessListener(), new VanishListener(), new PlayerLoginListener(), new BlockPlaceListener(), new StatisticCountListener(), new DiamondItemsListener(), new MysteryBoxListener());
        CoreUtil.sendConsoleMessage("Listeners registered");

        CoreUtil.sendConsoleMessage("Register commands...");
        API.registerCommand(new GamemodeCommand());
        API.registerCommand(new BroadcastCommand());
        API.registerCommand(new BroadCastTitleCommand());
        API.registerCommand(new ChangeItemCommand());
        API.registerCommand(new ClearCommand());
        API.registerCommand(new EnchantCommand());
        API.registerCommand(new FlyCommand());
        API.registerCommand(new GcCommand());
        API.registerCommand(new GiftCommand());
        API.registerCommand(new GiveCommand());
        API.registerCommand(new GodCommand());
        API.registerCommand(new HeadCommand());
        API.registerCommand(new HealCommand());
        API.registerCommand(new HelpOpCommand());
        API.registerCommand(new ItemCommand());
        API.registerCommand(new KickallCommand());
        API.registerCommand(new KickCommand());
        API.registerCommand(new OpenCommand());
        API.registerCommand(new PvPCommand());
        API.registerCommand(new SpeedCommand());
        API.registerCommand(new TeleportCommand());
        API.registerCommand(new TeleportHereCommand());
        API.registerCommand(new TimeCommand());
        API.registerCommand(new WhoisCommand());
        API.registerCommand(new ChatCommand());
        API.registerCommand(new VanishCommand());
        API.registerCommand(new BanCommand());
        API.registerCommand(new BanIPCommand());
        API.registerCommand(new TempBanCommand());
        API.registerCommand(new TempBanIPCommand());
        API.registerCommand(new KitCommand());
        API.registerCommand(new BackupCommand());
        API.registerCommand(new RankCommand());
        API.registerCommand(new CraftingCommand());
        API.registerCommand(new DropCommand());
        API.registerCommand(new LevelCommand());
        API.registerCommand(new SideBarCommand());
        API.registerCommand(new RefreshCommand());
        API.registerCommand(new UnBanCommand());
        API.registerCommand(new SpawnCommand());
        API.registerCommand(new SetSpawnCommand());
        API.registerCommand(new UnBanIPCommand());
        API.registerCommand(new WhiteListCommand());
        API.registerCommand(new EventCommand());
        API.registerCommand(new RankingCommand());
        API.registerCommand(new RankingSetCommand());
        API.registerCommand(new RankingResetCommand());
        API.registerCommand(new EnableCommand());
        API.registerCommand(new MysteryCommand());
        API.registerCommand(new AchivmentCommand());
        API.registerCommand(new DepositCommand());
        API.registerCommand(new ShopCommand());
        API.registerCommand(new GuildCommand());
        API.registerCommand(new EffectShopCommand());
        API.registerCommand(new TellCommand());
        API.registerCommand(new ReplyCommand());
        API.registerCommand(new TopCommand());
        API.registerCommand(new ServerManagerCommand());
        API.registerCommand(new CobblexCommand());
        CoreUtil.sendConsoleMessage("Commands registered");


        CoreUtil.sendConsoleMessage("Register tasks...");
        new CombatActionBarUpdate().runTaskTimerAsynchronously(this, 20L, TimeUtil.SECOND.getTick(1));
        new CombatTask().runTaskTimerAsynchronously(this, 40L, 20L);
        AntyMacroTask.getRate();
        new BackupTask().runTaskTimerAsynchronously(this, 20L, TimeUtil.SECOND.getTick(BackupConfig.BACKUP_AUTOMATIC_DELAY));
        new DepositTask().runTaskTimerAsynchronously(this, 100L, 200L);
        TabUpdateTask.type = TabListType.KILLS;
        new TabUpdateTask().runTaskTimerAsynchronously(this, 400L, TimeUtil.SECOND.getTick(12));
        new TopUpdateTask().runTaskTimerAsynchronously(this, 400L, TimeUtil.SECOND.getTick(30));
        new EventsCheckTask().runTaskTimer(this, 200L,TimeUtil.SECOND.getTick(10));
        CoreUtil.sendConsoleMessage("Tasks registered");

        CoreUtil.sendConsoleMessage("Register outgoingPluginChannel (BungeeCord)...");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        CoreUtil.sendConsoleMessage("outgoingPluginChannel (BungeeCord) registered");

        API.registerCommand(new debugcommand());
        DiscoUtil.implementDiscoArmors();
        serverManagerInventory = new ServerManagerInventory();
        CoreUtil.sendConsoleMessage("Enabled ProjectHardcore Core in " + (System.currentTimeMillis() - start)  + "ms");
    }
    @Override
    public void onDisable(){
        CoreUtil.sendConsoleMessage("Warning ! -> Core is going to disable all process !");
        CoreUtil.sendConsoleMessage("Plugin must kick all players from server and save them settings !");
        specialStoneManager.saveLocation();
        Bukkit.getScheduler().cancelTasks(this);
        for(Player p : Bukkit.getOnlinePlayers()){
            CombatManager.removeCombat(p);
            User u = getUserManager().getUser(p);
            u.update(true);
            getBackupManager().createBackup(p, BackupType.CLOSESERVER, "CONSOLE");
            p.kickPlayer("Warning -> SET CORE TO OFFLINE MODE -> SAVE YOUR DATA CHANGE TO 'DONE'");
        }
        for(Rank r : getRankManager().getRanks().values()){
            r.update(true);
            CoreUtil.sendConsoleMessage("RANKS -> SAVE DATA FOR " + r.getName() + " SET TO 'DONE' !");
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(Core.getTextCommandManager().getCommands().stream().anyMatch(command.getName()::equalsIgnoreCase)){
            TextCommand textCommand = Core.getTextCommandManager().getTextCommand(command.getName());
            if(sender.hasPermission(textCommand.getPermission())){
                textCommand.getText().forEach(string -> Util.sendMsg(sender,Util.fixColor(Util.replaceString(string))));
            }else{
                return Util.sendMsg(sender, Util.replaceString("&4%V% &cNie posiadasz uprawnien &8(&4" + textCommand.getPermission() + "&8)"));
            }
        }
        return false;
    }
}
/*TODO
TODO 5 PANEL ADMINISTRACJI
TODO 7 INCOGNITO
TODO 8 GILDIE ITP
TODO case open
TODO TAGI
TODO smierc po logut podczas pvp
TODO linia pvp
*/

