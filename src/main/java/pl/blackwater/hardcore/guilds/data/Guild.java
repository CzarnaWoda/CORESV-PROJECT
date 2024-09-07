package pl.blackwater.hardcore.guilds.data;

import lombok.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.utils.LocationUtil;
import pl.blackwater.hardcore.utils.MaterialUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.store.Entry;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.MathUtil;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
@Setter
public class Guild implements Colors, Entry {

    private String name, tag;
    private Set<Member> members;
    private UUID owner;
    private boolean pvp, preDeleted;
    private long createTime, expireTime, lastExplodeTime, liveCool;
    private int playerLimits, lives, deposit;
    private Set<UUID> invites;
    private Location homeLocation;
    private Cuboid cuboid;
    private InventoryGUI guildInventory,depositInventory;

    public Guild(String tag, String name, Player owner) {
        this.name = name;
        this.tag = tag;
        this.owner = owner.getUniqueId();
        this.members = new HashSet<>();
        addMember(owner, Position.LIDER);
        this.pvp = false;
        this.preDeleted = false;
        this.createTime = System.currentTimeMillis();
        this.expireTime = System.currentTimeMillis() + TimeUtil.DAY.getTime(7);
        this.lastExplodeTime = 0L;
        this.liveCool = System.currentTimeMillis() + TimeUtil.DAY.getTime(1);
        this.playerLimits = GuildConfig.GUILD_MEMBERS_LIMIT_START;
        this.lives = 3;
        this.invites = new HashSet<>();
        this.homeLocation = owner.getLocation();
        this.cuboid = new Cuboid(owner.getLocation().getWorld(), owner.getLocation().getBlockX(), owner.getLocation().getBlockZ(), GuildConfig.CUBOID_SIZE_START);
        this.deposit = 0;
        this.guildInventory = buildInventory();
        this.depositInventory = buildDepositInventory();
        insert();
    }

    public Guild(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.tag = rs.getString("tag");
        this.owner = UUID.fromString(rs.getString("owner"));
        this.members = new HashSet<>();
        this.pvp = rs.getBoolean("pvp");
        this.preDeleted = false;
        this.createTime = rs.getLong("createTime");
        this.expireTime = rs.getLong("expireTime");
        this.lastExplodeTime = 0L;
        this.liveCool = rs.getLong("liveCool");
        this.playerLimits = rs.getInt("playerLimits");
        this.lives = rs.getInt("lives");
        this.invites = new HashSet<>();
        this.homeLocation = LocationUtil.convertStringToBlockLocation(rs.getString("homeLocation"));
        this.cuboid = new Cuboid(Bukkit.getWorld(rs.getString("cuboidWorld")), rs.getInt("cuboidX"), rs.getInt("cuboidZ"), rs.getInt("cuboidSize"));
        this.deposit = rs.getInt("deposit");
        this.guildInventory = buildInventory();
        this.depositInventory = buildDepositInventory();
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM `{P}guilds` WHERE `tag`='" + getTag() + "';";
        API.getStore().update(sql);
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO `{P}guilds`(`id`,`name`,`tag`,`owner`,`pvp`,`createTime`,`expireTime`,`liveCool`,`playerLimits`,`lives`,`homeLocation`,`cuboidWorld`,`cuboidX`,`cuboidZ`,`cuboidSize`,`deposit`) VALUES (NULL, '" + getName() + "', '" + getTag() + "', '" + getOwner().toString() + "', '" + (isPvp() ? 1 : 0) + "', '" + getCreateTime() + "', '" + getExpireTime() + "', '" + getLiveCool() + "', '" + getPlayerLimits() + "', '" + getLives() + "', '" + LocationUtil.convertLocationBlockToString(getHomeLocation()) + "', '" + getCuboid().getWorld().getName() + "', '" + getCuboid().getCenterX() + "', '" + getCuboid().getCenterZ() + "', '" + getCuboid().getSize() + "', '" + getDeposit() + "');";
        API.getStore().update(sql);
    }

    @Override
    public void update(boolean now) {
        String sql = "UPDATE `{P}guilds` SET `owner`='" + getOwner().toString() + "',`pvp`='" + (isPvp() ? 1 : 0) + "',`expireTime`='" + getExpireTime() + "',`playerLimits`='" + getPlayerLimits() + "',`lives`='" + getLives() + "',`cuboidSize`='" + getCuboid().getSize() + "',`homeLocation`='" + LocationUtil.convertLocationBlockToString(homeLocation) + "',`deposit`='" + getDeposit() + "'WHERE `tag`='" + getTag() + "'";
        API.getStore().update(now, sql);
    }

    public void addMember(Player player) {
        final Member member = new Member(player.getUniqueId(), this, Position.MEMBER);
        getMembers().add(member);
        Core.getMemberManager().getMembers().put(player.getUniqueId(), member);
    }
    public void addMember(Player player, Position position) {
        final Member member = new Member(player.getUniqueId(), this, position);
        getMembers().add(member);
        Core.getMemberManager().getMembers().put(player.getUniqueId(), member);
    }
    public void removeMember(Player player) {
        final Member member = Core.getMemberManager().getMember(player);
        if (member != null && getMembers().contains(member)) {
            getMembers().remove(member);
            member.delete();
            Core.getMemberManager().getMembers().remove(member.getUuid());
        }
    }
    public void removeMember(Member member) {
        Core.getMemberManager().getMembers().remove(member.getUuid());
        getMembers().remove(member);
        member.delete();
    }
    public OfflinePlayer getGuildLider() {
        return Bukkit.getOfflinePlayer(getOwner());
    }

    public OfflinePlayer getGuildPreLider() {
        for (Member member : getMembers()) {
            if (member.getPosition().equals(Position.PRELIDER)) {
                return Bukkit.getOfflinePlayer(member.getUuid());
            }
        }
        return null;
    }
    private void updateDepositStatus(ItemStack itemStack){
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Aktualnie w skarbcu: " + ImportantColor + UnderLined + getDeposit() + " monet"))));
        itemStack.setItemMeta(itemMeta);
    }
    public InventoryGUI buildDepositInventory(){
        InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Skarbiec " + ImportantColor + "gildii " + SpecialSigns + "<<-")), 1);
        final ItemBuilder add100 = new ItemBuilder(Material.STAINED_CLAY,1,(short)5)
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wplac" + SpecialSigns + " " + ImportantColor + UnderLined + "100 monet")));
        final ItemBuilder add1000 = new ItemBuilder(Material.STAINED_CLAY,1,(short)13)
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wplac" + SpecialSigns + " " + ImportantColor + UnderLined + "1000 monet")));
        final ItemBuilder remove100 = new ItemBuilder(Material.STAINED_CLAY,1,(short)6)
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wyplac" + SpecialSigns + " " + WarningColor + UnderLined + "100 monet")));
        final ItemBuilder remove1000 = new ItemBuilder(Material.STAINED_CLAY,1,(short)14)
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wyplac" + SpecialSigns + " " + WarningColor + UnderLined + "1000 monet")));
        final ItemBuilder depositStatus = new ItemBuilder(Material.GOLD_BLOCK).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Status " + ImportantColor + "skarbca")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Aktualnie w skarbcu: " + ImportantColor + UnderLined + getDeposit() + " monet")));
        inv.setItem(0,add1000.build(),(player, inventory, i, itemStack) -> {
            final User user = Core.getUserManager().getUser(player);
            if(user.getCoins() >= 1000){
                user.removeCoins(1000);
                addDeposit(1000);
                Util.sendMsg(player, guildPrefix + "Poprawnie dodales " + ImportantColor + "1000 " + MainColor + "monet do skarbca");
                updateDepositStatus(inventory.getItem(4));
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz tyle monet");
            }
        });
        inv.setItem(1,add100.build(),(player, inventory, i, itemStack) -> {
            final User user = Core.getUserManager().getUser(player);
            if(user.getCoins() >= 100){
                user.removeCoins(100);
                addDeposit(100);
                Util.sendMsg(player, guildPrefix + "Poprawnie dodales " + ImportantColor + "100 " + MainColor + "monet do skarbca");
                updateDepositStatus(inventory.getItem(4));
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz tyle monet");
            }
        });
        inv.setItem(8,remove1000.build(),(player, inventory, i, itemStack) -> {
            final User user = Core.getUserManager().getUser(player);
            if(getDeposit() >= 1000){
                user.addCoins(1000);
                removeDeposit(1000);
                Util.sendMsg(player, guildPrefix + "Poprawnie zabrales " + ImportantColor + "1000 " + MainColor + "monet z skarbca");
                updateDepositStatus(inventory.getItem(4));
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Gildia nie posiada tyle monet w skarbcu");
            }
        });
        inv.setItem(7,remove100.build(),(player, inventory, i, itemStack) -> {
            final User user = Core.getUserManager().getUser(player);
            if(getDeposit() >= 100){
                user.addCoins(100);
                removeDeposit(100);
                Util.sendMsg(player, guildPrefix + "Poprawnie zabrales " + ImportantColor + "100 " + MainColor + "monet z skarbca");
                updateDepositStatus(inventory.getItem(4));
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Gildia nie posiada tyle monet w skarbcu");
            }
        });
        inv.setItem(4, depositStatus.build(),null);
        return inv;
    }
    public InventoryGUI buildInventory() {
        InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zarzadzanie " + ImportantColor + "gildia " + SpecialSigns + "<<-")), 4);
        final ItemBuilder magneta = MaterialUtil.getStainedGlassPane((short)2);
        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short)11);
        inv.setItem(0,magneta.build(),null);
        inv.setItem(1,magneta.build(),null);
        inv.setItem(2,blue.build(),null);
        inv.setItem(3,magneta.build(),null);
        inv.setItem(4,blue.build(),null);
        inv.setItem(5,blue.build(),null);
        inv.setItem(6,magneta.build(),null);
        inv.setItem(7,magneta.build(),null);
        inv.setItem(8,magneta.build(),null);
        inv.setItem(9,magneta.build(),null);
        inv.setItem(10,blue.build(),null);
        final int[] cost = {(getCuboid().getSize() - GuildConfig.CUBOID_SIZE_START + 1) * GuildConfig.GUILD_UPGRADES_CUBOID, (getPlayerLimits() - GuildConfig.GUILD_MEMBERS_LIMIT_START + 1) * GuildConfig.GUILD_UPGRADES_PLAYERLIMIT,GuildConfig.GUILD_UPGRADES_EXPIRETIME};
        final ItemBuilder grass = new ItemBuilder(Material.GRASS).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Powiekszenie gildii" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny rozmiar gildii: " + ImportantColor + UnderLined + getCuboid().getSize() + SpecialSigns + "*" + ImportantColor + UnderLined + getCuboid().getSize())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Powiekszenie doda: " + ImportantColor + UnderLined + (getCuboid().getSize() >= GuildConfig.CUBOID_SIZE_MAX ? "MAX" : "" + GuildConfig.CUBOID_SIZE_ADD + SpecialSigns + "*" + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_ADD))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Maksymalny rozmiar gildii: " + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_MAX + SpecialSigns + "*" + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_MAX)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena powiekszenia: " + ImportantColor + UnderLined + (getCuboid().getSize() >= GuildConfig.CUBOID_SIZE_MAX ? "MAX" : cost[0] + "" + MainColor + " monet"))));
        inv.setItem(11, grass.build(), (player, inventory, i, itemStack) -> {
            if(getCuboid().getSize() < GuildConfig.CUBOID_SIZE_MAX && getCuboid().getSize() + GuildConfig.CUBOID_SIZE_ADD <= GuildConfig.CUBOID_SIZE_MAX){
                if(getDeposit() >= cost[0]){
                    InventoryGUI yesorno = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Potwierdzenie " + ImportantColor + "powiekszenia gildii " + SpecialSigns + "<<-")), 1);
                    final ItemBuilder yes = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.GREEN + "&lPOTWIERDZ")))
                            .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Z skarbca gildii zostanie zabrane " + ImportantColor + cost[0] + MainColor + " monet")));
                    final ItemBuilder no = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.DARK_RED + "&lANULUJ")));
                    yesorno.setItem(0,yes.build(),(p, inventory1, i1, itemStack1) -> {
                        if(getCuboid().addSize()){
                            removeDeposit(cost[0]);
                            Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(guildPrefix + "Gildia " + SpecialSigns + "[" + ImportantColor + getTag() + SpecialSigns + "]" + ImportantColor + getName() + MainColor + " powiekszyla swoj teren do " + ImportantColor + getCuboid().getSize() + SpecialSigns + "*" + ImportantColor + getCuboid().getSize())));
                            final ItemMeta meta = itemStack.getItemMeta();
                            final int newcost = (getCuboid().getSize() - GuildConfig.CUBOID_SIZE_START + 1) * GuildConfig.GUILD_UPGRADES_CUBOID;
                            cost[0] = newcost;
                            meta.setLore(Arrays.asList(
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny rozmiar gildii: " + ImportantColor + UnderLined + getCuboid().getSize() + SpecialSigns + "*" + ImportantColor + UnderLined + getCuboid().getSize())),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Powiekszenie doda: " + ImportantColor + UnderLined + (getCuboid().getSize() >= GuildConfig.CUBOID_SIZE_MAX ? "MAX" : "" + GuildConfig.CUBOID_SIZE_ADD + SpecialSigns + "*" + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_ADD))),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Maksymalny rozmiar gildii: " + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_MAX + SpecialSigns + "*" + ImportantColor + UnderLined + GuildConfig.CUBOID_SIZE_MAX)),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena powiekszenia: " + ImportantColor + UnderLined + (getCuboid().getSize() >= GuildConfig.CUBOID_SIZE_MAX ? "MAX" : newcost + "" + MainColor + " monet")))));
                            itemStack.setItemMeta(meta);
                            p.closeInventory();
                            inv.openInventory(p);
                        }
                    });
                    yesorno.setItem(8,no.build(),(p, inventory1, i1, itemStack1) -> {
                        p.closeInventory();
                        inv.openInventory(p);
                    });
                    player.closeInventory();
                    yesorno.openInventory(player);
                }else{
                    Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia ma za malo monet w skarbcu");
                }
            }else{
                Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia ma juz maksymalny rozmiar");
            }
        });
        inv.setItem(12,blue.build(),null);
        inv.setItem(13,blue.build(),null);
        final ItemBuilder chest = new ItemBuilder(Material.CHEST).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Powiekszenie limitu czlonkow" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny limit: " + ImportantColor + UnderLined + getPlayerLimits())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Maksymalny limit czlonkow: " + ImportantColor + UnderLined + GuildConfig.GUILD_MEMBERS_LIMIT_MAX)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena powiekszenia limitu: " + ImportantColor + UnderLined + (getPlayerLimits() >= GuildConfig.GUILD_MEMBERS_LIMIT_MAX ? "MAX" : cost[1] + "" + MainColor + " monet"))));
        inv.setItem(14,chest.build(),(player, inventory, i, itemStack) -> {
            if(getPlayerLimits() < GuildConfig.GUILD_MEMBERS_LIMIT_MAX){
                if(getDeposit() >= cost[1]){
                    InventoryGUI yesorno = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Potwierdzenie " + ImportantColor + "powiekszenia gildii " + SpecialSigns + "<<-")), 1);
                    final ItemBuilder yes = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.GREEN + "&lPOTWIERDZ")))
                            .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Z skarbca gildii zostanie zabrane " + ImportantColor + cost[1] + MainColor + " monet")));
                    final ItemBuilder no = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.DARK_RED + "&lANULUJ")));
                    yesorno.setItem(0,yes.build(),(p, inventory1, i1, itemStack1) -> {
                        if(getPlayerLimits() < GuildConfig.GUILD_MEMBERS_LIMIT_MAX){
                            addPlayerLimit(1);
                            removeDeposit(cost[1]);
                            Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(guildPrefix + "Gildia " + SpecialSigns + "[" + ImportantColor + getTag() + SpecialSigns + "]" + ImportantColor + getName() + MainColor + " powiekszyla limit czlonkow do " + ImportantColor + getPlayerLimits())));
                            final ItemMeta meta = itemStack.getItemMeta();
                            final int newcost = (getPlayerLimits() - GuildConfig.GUILD_MEMBERS_LIMIT_START + 1) * GuildConfig.GUILD_UPGRADES_PLAYERLIMIT;
                            cost[1] = newcost;
                            meta.setLore(Arrays.asList(
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny limit: " + ImportantColor + UnderLined + getPlayerLimits())),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Maksymalny limit czlonkow: " + ImportantColor + UnderLined + GuildConfig.GUILD_MEMBERS_LIMIT_MAX)),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena powiekszenia limitu: " + ImportantColor + UnderLined + (getPlayerLimits() >= GuildConfig.GUILD_MEMBERS_LIMIT_MAX ? "MAX" : cost[1] + "" + MainColor + " monet")))));
                            itemStack.setItemMeta(meta);
                            p.closeInventory();
                            inv.openInventory(p);
                        }
                    });
                    yesorno.setItem(8,no.build(),(p, inventory1, i1, itemStack1) -> {
                        p.closeInventory();
                        inv.openInventory(p);
                    });
                    player.closeInventory();
                    yesorno.openInventory(player);
                }else{
                    Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia ma za malo monet w skarbcu");
                }
            }else{
                Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia ma juz maksymalny limit czlonkow");
            }
        });
        inv.setItem(15,blue.build(),null);
        inv.setItem(16,blue.build(),null);
        inv.setItem(17,blue.build(),null);
        inv.setItem(18,blue.build(),null);
        inv.setItem(19,blue.build(),null);
        inv.setItem(20,blue.build(),null);
        //anmvil inv.setItem(21,blue.build(),null);
        final ItemBuilder anvil = new ItemBuilder(Material.ANVIL).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Przedluzenie waznosci gildii" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Gildia wygasa za: " + ImportantColor + UnderLined + Util.secondsToString((int) ((getExpireTime() - System.currentTimeMillis())/1000L)))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena przedluzenia czasu: " + ImportantColor + UnderLined + (getExpireTime() - System.currentTimeMillis() >= TimeUtil.DAY.getTime(14) ? "MAX" : cost[2] + "" + MainColor + " monet"))));
        inv.setItem(21,anvil.build(),(player, inventory, i, itemStack) -> {
            if(getExpireTime() + TimeUtil.DAY.getTime(1) < System.currentTimeMillis() + TimeUtil.DAY.getTime(14)){
                if(getDeposit() >= cost[2]){
                    InventoryGUI yesorno = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Potwierdzenie " + ImportantColor + "przedluzenia gildii " + SpecialSigns + "<<-")), 1);
                    final ItemBuilder yes = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.GREEN + "&lPOTWIERDZ")))
                            .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Z skarbca gildii zostanie zabrane " + ImportantColor + cost[2] + MainColor + " monet")));
                    final ItemBuilder no = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(Util.fixColor(Util.replaceString(ChatColor.DARK_RED + "&lANULUJ")));
                    yesorno.setItem(0,yes.build(),(p, inventory1, i1, itemStack1) -> {
                        if(getExpireTime() + TimeUtil.DAY.getTime(1) < System.currentTimeMillis() + TimeUtil.DAY.getTime(14)){
                            addExpireTime(TimeUtil.DAY.getTick(1));
                            removeDeposit(cost[2]);
                            Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(guildPrefix + "Gildia " + SpecialSigns + "[" + ImportantColor + getTag() + SpecialSigns + "]" + ImportantColor + getName() + MainColor + " przedluzyla swoja waznosc o " + ImportantColor + "1 dzien")));
                            final ItemMeta meta = itemStack.getItemMeta();
                            meta.setLore(Arrays.asList(
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Gildia wygasa za: " + ImportantColor + UnderLined + Util.secondsToString((int) ((getExpireTime() - System.currentTimeMillis())/1000L)))),
                                    Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Cena przedluzenia czasu: " + ImportantColor + UnderLined + (getExpireTime() - System.currentTimeMillis() >= TimeUtil.DAY.getTime(14) ? "MAX" : cost[2] + "" + MainColor + " monet")))));
                            itemStack.setItemMeta(meta);
                            p.closeInventory();
                            inv.openInventory(p);
                        }
                    });
                    yesorno.setItem(8,no.build(),(p, inventory1, i1, itemStack1) -> {
                        p.closeInventory();
                        inv.openInventory(p);
                    });
                    player.closeInventory();
                    yesorno.openInventory(player);
                }else{
                    Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia ma za malo monet w skarbcu");
                }
            }else{
                Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Twoja gildia osiagnela juz maksymalny czas do wygasniecia");
            }
        });
        inv.setItem(22,blue.build(),null);
        inv.setItem(23,blue.build(),null);
        final ItemBuilder diamondsword = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Status PvP w gildii" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie: " + (isPvp() ? ChatColor.GREEN + "%V%": ChatColor.DARK_RED + "%X%"))));
        inv.setItem(24,diamondsword.build(),(player, inventory, i, itemStack) -> {
            setPvp(!isPvp());
            final ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie: " + (isPvp() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%")))));
            itemStack.setItemMeta(meta);
            for(Member members : Core.getMemberManager().getMembers(this)){
                Util.sendMsg(members.getPlayer() , guildPrefix + "PvP w twojej gildi zostalo " + (isPvp() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone"));
            }
        });
        inv.setItem(25,blue.build(),null);
        inv.setItem(26,magneta.build(),null);
        inv.setItem(27,magneta.build(),null);
        inv.setItem(28,magneta.build(),null);
        inv.setItem(29,magneta.build(),null);
        inv.setItem(30,blue.build(),null);
        inv.setItem(31,blue.build(),null);
        inv.setItem(32,magneta.build(),null);
        inv.setItem(33,blue.build(),null);
        inv.setItem(34,magneta.build(),null);
        inv.setItem(35,magneta.build(),null);
        return inv;
    }
    public void removeDeposit(int coins){
        this.deposit -= coins;
        update(false);
        updateDepositStatus(getDepositInventory().getItem(4));
    }
    public void addPlayerLimit(int limit){
        this.playerLimits += limit;
        update(false);
    }
    public void addExpireTime(long time){
        this.expireTime += time;
        update(false);
    }
    public void addDeposit(int coins){
        this.deposit += coins;
        update(false);
    }
    public int getKills(){
        int kills = 0;
        for(Member m : Core.getMemberManager().getMembers(this)){
            User u = Core.getUserManager().getUser(m.getUuid());
            kills += u.getKills();
        }
        return kills;
    }
    public int getDeaths(){
        int deaths = 0;
        for(Member m : Core.getMemberManager().getMembers(this)){
            User u = Core.getUserManager().getUser(m.getUuid());
            deaths += u.getDeaths();
        }
        return deaths;
    }
    public int getPoints(){
        int points = 0;
        return (Math.max(points, -5));
    }
    public void sendMessage(String message){
        for(Member member : Core.getMemberManager().getOnlineMembers(this)){
            Util.sendMsg(member.getPlayer(), Util.fixColor(Util.replaceString(message)));
        }
    }
    public double getKD(){
        if(getKills() == 0 && getDeaths() == 0){
            return 0.0D;
        }
        if(getKills() > 0 && getDeaths() == 0){
            return getKills();
        }
        if(getDeaths() > 0 && getKills() == 0){
            return -getDeaths();
        }
        return MathUtil.round(getKills()/getDeaths(), 2);
    }
}
