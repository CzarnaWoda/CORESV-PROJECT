package pl.blackwater.hardcore.data;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.enums.AchievementType;
import pl.blackwater.hardcore.enums.MessageType;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.storage.RankStorage;
import pl.blackwater.hardcore.utils.ListUtil;
import pl.blackwater.hardcore.utils.LocationUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;
import pl.blackwaterapi.utils.Base64Util;
import pl.blackwaterapi.utils.MathUtil;
import pl.blackwaterapi.utils.Util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
public class User implements Entry {

    private UUID uuid;
    private String lastName,firstIP,lastIP,lastKill;
    private long lastJoin,lastKillTime,turboDropTime,turboExpTime;
    private int kills,deaths,logouts,assists,points,timePlay,depositKox,depositRef,depositPearl,coins,level,eatedRef,eatedKox,throwedPearl,openedChests;
    private boolean fly,god,turboDrop,turboExp;
    private GameMode gameMode;
    private Location homeLocation,lastLocation;
    private List<Long> kitTimes;
    private List<String> takenAchievements;
    private List<Integer> drops;
    private Rank rank;
    private double bonusDrop,exp;
    private ItemStack[] enderChest;
    private PlayerClassType playerClassType;
    private List<MessageType> messages;

    public User(Player player){
        uuid = player.getUniqueId();
        lastName = player.getDisplayName();
        firstIP = player.getAddress().getHostString();
        lastIP = player.getAddress().getHostString();
        lastKill = "";
        lastJoin = System.currentTimeMillis();
        kitTimes = Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L);
        drops = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        takenAchievements = new ArrayList<>();
        lastKillTime = 0L;
        kills = 0;
        deaths = 0;
        logouts = 0;
        assists = 0;
        points = 0;
        timePlay = 0;
        depositKox = 0;
        depositPearl = 0;
        depositRef = 0;
        openedChests = 0;
        eatedRef = 0;
        eatedKox = 0;
        throwedPearl = 0;
        fly = player.getAllowFlight();
        god = (player.getGameMode() == GameMode.CREATIVE);
        gameMode = player.getGameMode();
        homeLocation = player.getWorld().getSpawnLocation();
        lastLocation = player.getLocation();
        rank = Core.getRankManager().getRank(RankStorage.DEFAULT_RANK);
        turboDropTime = 0L;
        turboExpTime = 0L;
        turboDrop = false;
        turboExp = false;
        bonusDrop = 0.0;
        coins = 0;
        exp = 0.0;
        level = 0;
        enderChest = player.getEnderChest().getContents();
        playerClassType = PlayerClassType.UNKNOWN;
        messages = new ArrayList<>();
        insert();
    }
    public User(ResultSet rs) throws SQLException, IOException {
        uuid = UUID.fromString(rs.getString("uuid"));
        lastName = rs.getString("lastName");
        firstIP = rs.getString("firstIP");
        lastIP = rs.getString("lastIP");
        lastKill = rs.getString("lastKill");
        lastJoin = rs.getLong("lastJoin");
        kitTimes = ListUtil.convertStringToLongList(rs.getString("kitTimes"));
        drops = ListUtil.convertStringToIntegerList(rs.getString("drops"));
        lastKillTime = rs.getLong("lastKillTime");
        kills = rs.getInt("kills");
        deaths = rs.getInt("deaths");
        logouts = rs.getInt("logouts");
        assists = rs.getInt("assists");
        points = rs.getInt("points");
        timePlay = rs.getInt("timePlay");
        depositKox = rs.getInt("depositKox");
        depositPearl = rs.getInt("depositPearl");
        depositRef = rs.getInt("depositRef");
        eatedKox = rs.getInt("eatedKox");
        eatedRef = rs.getInt("eatedRef");
        throwedPearl = rs.getInt("throwedPearl");
        openedChests = rs.getInt("openedChests");
        fly = (rs.getInt("fly") == 1);
        god = (rs.getInt("god") == 1);
        gameMode = GameMode.getByValue(rs.getInt("gameMode"));
        homeLocation = LocationUtil.convertStringToLocation(rs.getString("homeLocation"));
        lastLocation = LocationUtil.convertStringToLocation(rs.getString("lastLocation"));
        rank = Core.getRankManager().getRank(rs.getString("rank"));
        turboDropTime = rs.getLong("turboDropTime");
        turboExpTime = rs.getLong("turboExpTime");
        turboDrop = rs.getInt("turboDrop") == 1;
        turboExp = rs.getInt("turboExp") == 1;
        bonusDrop = rs.getDouble("bonusDrop");
        coins = rs.getInt("coins");
        exp = rs.getDouble("exp");
        level = rs.getInt("level");
        enderChest = Base64Util.itemStackArrayFromBase64(rs.getString("enderChest"));
        takenAchievements = ListUtil.convertStringToList(rs.getString("takenAchievements"));
        playerClassType = PlayerClassType.getByName(rs.getString("playerClass"));
        messages = ListUtil.convertStringToMessageTypes(rs.getString("messages"));
    }
    @Override
    public void insert() {
        String sql = "INSERT INTO `{P}users` (`id`,`uuid`,`lastName`,`firstIP`,`lastIP`,`lastKill`,`lastJoin`,`kitTimes`,`drops`,`lastKillTime`,`kills`,`deaths`,`logouts`,`assists`,`points`,`timePlay`,`depositKox`,`depositRef`,`depositPearl`,`fly`,`god`,`gameMode`,`homeLocation`,`lastLocation`,`rank`,`turboDropTime`,`turboExpTime`,`turboDrop`,`turboExp`,`bonusDrop`,`coins`,`exp`,`level`,`eatedKox`,`eatedRef`,`throwedPearl`,`enderChest`,`takenAchievements`,`openedChests`,`playerClass`,`messages`) VALUES (NULL,'" + getUuid().toString() + "', '" + getLastName() + "', '" + getFirstIP() + "', '" + getLastIP() + "', '" + getLastKill() + "', '" + getLastJoin() + "', '" + ListUtil.convertLongListToString(getKitTimes()) + "', '" + ListUtil.convertIntegerListToString(getDrops()) + "', '" + getLastKillTime() + "', '" + getKills() + "', '" + getDeaths() + "', '" + getLogouts() + "', '" + getAssists() + "', '" + getPoints() + "', '" + getTimePlay() + "', '" + getDepositKox() + "', '" + getDepositRef() + "', '" + getDepositPearl() + "', '" + (isFly() ? 1 : 0) + "', '" + (isGod() ? 1 : 0) + "', '" + getGameMode().getValue() + "', '" + LocationUtil.convertLocationToString(getHomeLocation()) + "', '" + LocationUtil.convertLocationToString(getLastLocation()) + "', '" + getRank().getName() + "', '" + getTurboDropTime() + "', '" + getTurboExpTime() + "', '" + (isTurboDrop() ? 1 : 0) + "', '" + (isTurboExp() ? 1 : 0) + "', '" + (MathUtil.round(getBonusDrop(), 2)) + "', '" + getCoins() + "', '" + (MathUtil.round(getExp(), 2) + "', '" + getLevel() + "', '" + getEatedKox() + "', '" + getEatedRef() + "', '" + getThrowedPearl() + "', '" + Base64Util.itemStackArrayToBase64(getEnderChest()) + "','" + ListUtil.convertListToString(getTakenAchievements()) + "', '" + getOpenedChests() + "', '" + getPlayerClassType().getName() + "', '" + ListUtil.convertMessageTypesToString(getMessages()) + "')");
        API.getStore().update(sql);
    }

    @Override
    public void update(boolean b) {
        String sql = "UPDATE `{P}users` SET `uuid`='" + getUuid().toString() + "',`lastName`='" + getLastName() + "',`firstIP`='" + getFirstIP() + "',`lastIP`='" + getLastIP() + "',`lastKill`='" + getLastKill() + "',`lastJoin`='" + getLastJoin() + "',`kitTimes`='" + ListUtil.convertLongListToString(getKitTimes()) + "',`drops`='" + ListUtil.convertIntegerListToString(getDrops()) + "',`lastKillTime`='" + getLastKillTime() + "',`kills`='" + getKills() + "',`deaths`='" + getDeaths() + "',`logouts`='" + getLogouts() + "',`assists`='" + getAssists() + "',`points`='" + getPoints() + "',`timePlay`='" + getTimePlay() + "',`depositKox`='" + getDepositKox() + "',`depositRef`='" + getDepositRef() + "',`depositPearl`='" + getDepositPearl() + "',`fly`='" + (isFly() ? 1 : 0) + "',`god`='" + (isGod() ? 1 : 0) + "',`gameMode`='" + getGameMode().getValue() + "',`homeLocation`='" + LocationUtil.convertLocationToString(getHomeLocation()) + "',`lastLocation`='" + LocationUtil.convertLocationToString(getLastLocation()) + "',`rank`='" + getRank().getName() + "',`turboDropTime`='" + getTurboDropTime() + "',`turboExpTime`='" + getTurboExpTime() + "',`turboDrop`='" + (isTurboDrop() ? 1 : 0) + "',`turboExp`='" + (isTurboExp() ? 1 : 0) + "',`bonusDrop`='" + MathUtil.round(getBonusDrop(), 2) + "',`coins`='" + getCoins() + "',`exp`='" + MathUtil.round(getExp(), 2) + "',`level`='" + getLevel() + "',`eatedKox`='" + getEatedKox() + "',`eatedRef`='" + getEatedRef() + "',`throwedPearl`='" + getThrowedPearl() + "',`enderChest`='" + Base64Util.itemStackArrayToBase64(getEnderChest()) + "',`takenAchievements`='" + ListUtil.convertListToString(getTakenAchievements()) + "',`openedChests`='" + getOpenedChests() + "',`playerClass`='" + getPlayerClassType().getName() + "',`messages`='" + ListUtil.convertMessageTypesToString(getMessages()) + "' WHERE `uuid`='" + getUuid() + "'";
        API.getStore().update(b, sql);
    }
    public void saveEnderChest(){
        String sql = "UPDATE `{P}users` SET `enderChest`='" + Base64Util.itemStackArrayToBase64(getEnderChest()) + "' WHERE `uuid`='" + getUuid() + "'";
        API.getStore().update(true, sql);
    }

    public boolean isAchievementCompleted(String achivment){
        for(String ach : getTakenAchievements()){
            if(ach.equals(achivment)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete() {
        API.getStore().update(false, "DELETE FROM `{P}users` WHERE `uuid` = '" + getUuid() + "'");
    }

    public void addAchievement(String achievementType){
        getTakenAchievements().add(achievementType);
    }

    public int getAchievementStat(AchievementType achievementType){
        switch (achievementType){
            case BREAK_STONE:
                return getDropStones();
            case EATED_KOX:
                return getEatedKox();
            case EATED_REF:
                return getEatedRef();
            case KILLS:
                return getKills();
            case THROWED_PEARL:
                return getThrowedPearl();
            case SPEND_TIME:
                return getTimePlay();
            case OPEN_CHESTS:
                return getOpenedChests();
        }
        return 0;
    }

    public void resetRanking(String stats){
        switch(stats){
            case "stats":
                kills = 0;
                deaths = 0;
                logouts = 0;
                assists = 0;
                points = 0;
                timePlay = 0;
                depositKox = 0;
                depositPearl = 0;
                depositRef = 0;
                eatedRef = 0;
                eatedKox = 0;
                throwedPearl = 0;
                turboDropTime = 0L;
                turboExpTime = 0L;
                turboDrop = false;
                turboExp = false;
                bonusDrop = 0.0;
                coins = 0;
                exp = 0.0;
                level = 0;
            case "allDatas":
                resetRanking("stats");
                if(getPlayer() != null){
                    Player player = getPlayer();
                    fly = player.getAllowFlight();
                    god = (player.getGameMode() == GameMode.CREATIVE);
                    gameMode = player.getGameMode();
                    homeLocation = player.getWorld().getSpawnLocation();
                    lastLocation = player.getLocation();
                }else{
                    fly = false;
                    god = false;
                    gameMode = GameMode.SURVIVAL;
                    homeLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
                    lastLocation = homeLocation;
                }
            case "withEnder":
                resetRanking("allDatas");
                if(getPlayer() != null){
                    Player player = getPlayer();
                    enderChest = player.getEnderChest().getContents();
                }else{
                    enderChest = new ItemStack[] {};
                }
            case "all":
                resetRanking("withEnder");
                rank = Core.getRankManager().getRank("default");
            case "playerClass":
                setPlayerClassType(PlayerClassType.UNKNOWN);
                if(getPlayer() != null){
                    getPlayer().kickPlayer(Util.fixColor(Util.replaceString(Colors.SpecialSigns + "->> " + Colors.MainColor + "Twoja klasa zostala " + Colors.ImportantColor + "zresetowana")));
                }
        }
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public void addTimePlay(int time){
        timePlay += time;
    }
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public void addLevel(int level){
        this.level += level;
    }

    public void addExp(double exp){
        this.exp += exp;
    }

    public void addEatedKox(int kox){
        eatedKox += kox;
    }

    public void addEatedRef(int ref){
        eatedRef += ref;
    }

    public void addThrowedPearl(int pearl){
        throwedPearl += pearl;
    }

    public void removeEatedKox(int kox){
        eatedKox -= kox;
    }

    public void removeEatedRef(int ref){
        eatedRef -= ref;
    }
    public void removeThrowedPearl(int pearl){
        throwedPearl -= pearl;
    }
    public void addAssist(int assists) { this.assists += assists;}
    public void addPoints(int points){
        this.points += points;
    }
    public void removePoints(int points){
        this.points -= points;
    }
    public void addDeaths(int deaths){
        this.deaths += deaths;
    }
    public void addKills(int kills){
        this.kills = kills;
    }
    public double getExpToLevel()
    {
        if (getLevel() >= 0 && getLevel() < 5){
            return MathUtil.round(45.2 * getLevel(), 2);
        }else
        if (getLevel() >= 5 && getLevel() < 20){
            return MathUtil.round(65.4 * getLevel(), 2);
        }	else
        if (getLevel() >= 20 && getLevel() < 35){
            return MathUtil.round(72.1 * getLevel(), 2);
        }		else
        if (getLevel() >= 35 && getLevel() < 60){
            return MathUtil.round(83.5 * getLevel(), 2);
        }
        else
        if (getLevel() >= 60 && getLevel() < 100){
            return MathUtil.round(103.7 * getLevel(), 2);
        }
        else{
            return MathUtil.round(125.4 * getLevel(), 2);
        }
    }
    public String getOnlineTime() {
        return Util.secondsToString((int)((int)(System.currentTimeMillis() - getLastJoin())/1000L)).replace(" ", "");
    }
    public void removeExp(double index){
        exp -= index;
    }

    public int getDropDiamonds(){
        return drops.get(0);
    }
    public int getDropEmeralds(){
        return drops.get(1);
    }
    public int getDropGolds(){
        return drops.get(2);
    }
    public int getDropIrons(){
        return drops.get(3);
    }
    public int getDropSands(){
        return drops.get(4);
    }
    public int getDropCoals(){
        return drops.get(5);
    }
    public int getDropRedStones(){
        return drops.get(6);
    }
    public int getDropLapises(){
        return drops.get(7);
    }
    public int getDropGunPowders(){
        return drops.get(8);
    }
    public int getDropPearls(){
        return drops.get(9);
    }
    public int getDropSlimeBalls(){
        return drops.get(10);
    }
    public int getDropApples(){
        return drops.get(11);
    }
    public int getDropBooks(){
        return drops.get(12);
    }
    public int getDropChests(){
        return drops.get(13);
    }
    public int getDropStones(){
        return drops.get(14);
    }

    public void setDropDiamonds(int diamonds){
        drops.set(0,diamonds);
    }
    public void setDropEmeralds(int emeralds){
        drops.set(1,emeralds);
    }
    public void setDropGolds(int golds){
        drops.set(2,golds);
    }
    public void setDropIrons(int irons){
        drops.set(3, irons);
    }
    public void setDropSands(int sands){
        drops.set(4,sands);
    }
    public void setDropCoals(int coals){
        drops.set(5,coals);
    }
    public void setDropRedStones(int redStones){
        drops.set(6,redStones);
    }
    public void setLapises(int lapises){
        drops.set(7,lapises);
    }
    public void setDropGunPowdres(int gunPowdres){
        drops.set(8,gunPowdres);
    }
    public void setDropPearls(int pearls){
        drops.set(9,pearls);
    }
    public void setDropSlimeBalls(int slimeBalls){
        drops.set(10,slimeBalls);
    }
    public void setDropApples(int apples){
        drops.set(11,apples);
    }
    public void setDropBooks(int books){
        drops.set(12,books);
    }
    public void setDropChests(int chests){
        drops.set(13,chests);
    }
    public void setDropStones(int stones){
        drops.set(14,stones);
    }

    public void addDropDiamonds(int diamonds){
        drops.set(0,getDropDiamonds() + diamonds);
    }
    public void addDropEmeralds(int emeralds){
        drops.set(1,getDropEmeralds() + emeralds);
    }
    public void addDropGolds(int golds){
        drops.set(2,getDropGolds() + golds);
    }
    public void addDropIrons(int irons){
        drops.set(3, getDropIrons() + irons);
    }
    public void addDropSands(int sands){
        drops.set(4,getDropSands() + sands);
    }
    public void addDropCoals(int coals){
        drops.set(5,getDropCoals() + coals);
    }
    public void addDropRedStones(int redStones){
        drops.set(6,getDropRedStones() + redStones);
    }
    public void addLapises(int lapises){
        drops.set(7,getDropLapises() + lapises);
    }
    public void addDropGunPowdres(int gunPowdres){
        drops.set(8,getDropGunPowders() + gunPowdres);
    }
    public void addDropPearls(int pearls){
        drops.set(9,getDropPearls() + pearls);
    }
    public void addDropSlimeBalls(int slimeBalls){
        drops.set(10,getDropSlimeBalls() + slimeBalls);
    }
    public void addDropApples(int apples){
        drops.set(11,getDropApples() + apples);
    }
    public void addDropBooks(int books){
        drops.set(12,getDropBooks() + books);
    }
    public void addDropChests(int chests){
        drops.set(13,getDropChests() + chests);
    }
    public void addDropStones(int stones){
        drops.set(14,getDropStones() + stones);
    }

    public void addDepositKox(int kox){
        this.depositKox +=kox;
    }
    public void addDepositRef(int ref){
        this.depositRef +=ref;
    }
    public void addDepositPearl(int pearl){
        this.depositPearl +=pearl;
    }
    public void removeDepositKox(int kox){
        this.depositKox -=kox;
    }
    public void removeDepositRef(int ref){
        this.depositRef -=ref;
    }
    public void removeDepositPearl(int pearl){
        this.depositPearl -=pearl;
    }
    public boolean isPremium(){
        return !getRank().getName().equalsIgnoreCase("gracz");
    }
    public void removeCoins(int coins){
        this.coins -= coins;
    }
    public void addCoins(int coins){
        this.coins += coins;
    }
    public double getKd() {
        if (getKills() == 0 && getDeaths() == 0) {
            return 0;
        }
        else
        if (getKills() > 0 && getDeaths() == 0) {
            return getKills();
        }
        else
        if (getDeaths() > 0 && getKills() == 0) {
            return -getDeaths();
        }
        else {
            return MathUtil.round(getKills() / (double) getDeaths(), 2);
        }
    }
    public boolean isMessage(MessageType type){
        if(getMessages().contains(type)){
            return true;
        }
        return false;
    }

}
