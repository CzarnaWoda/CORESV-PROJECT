package pl.blackwater.hardcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.data.RankSet;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.managers.RankSetManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwater.hardcore.settings.TabListConfig;
import pl.blackwater.hardcore.tablist.AbstractTablist;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Util;

public class PlayerJoinQuitListener implements Listener, Colors {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();
        User u = Core.getUserManager().getUser(player);
        if(u == null){
            u = Core.getUserManager().createUser(player);
        }
        Combat combat = CombatManager.getCombat(player);
        if (combat == null) {
            CombatManager.createCombat(player);
        }
        final Location loc = u.getLastLocation();
        if(loc != null){
            player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
        AbstractTablist abstractTablist = AbstractTablist.createTablist(TabListConfig.playerList,TabListConfig.TABLIST_HEADER,TabListConfig.TABLIST_FOOTER,9999,player);
        abstractTablist.send();
        Core.getUserManager().joinToGame(player);
        Core.getRankManager().joinPlayer(player);
        Core.getRankManager().implementPermissions(u);
        e.setJoinMessage(null);

        for(String s : MessageConfig.MESSAGE_PLAYERJOIN){
            Util.sendMsg(player, Util.replaceString(s.replace("{PLAYER}", player.getName()).replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{REGISTERPLAYERS}", String.valueOf(Core.getUserManager().getUsers().size()))));
        }
        final RankSet rankSet = RankSetManager.getSetRank(u);
        if(rankSet != null) {
            if(rankSet.getExpireTime() != 0L && rankSet.getExpireTime() <= System.currentTimeMillis()){
                ActionBarUtil.sendActionBar(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Twoja ranga " + ImportantColor + rankSet.getRank() + MainColor + " wlasnie " + ImportantColor + " wygasla")));
                RankSetManager.removeRank(u, rankSet);
            }else {
                ActionBarUtil.sendActionBar(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz range " + ImportantColor + rankSet.getRank() + MainColor + " " + (rankSet.getExpireTime() == 0L ? "na " + ImportantColor + "zawsze" : "do " + ImportantColor + Util.getDate(rankSet.getExpireTime())))), 20 * 5);
            }
        }
        final User user = u;
        Bukkit.getScheduler().runTaskLater(Core.getInstance(),() -> {
            if(user.getPlayerClassType().equals(PlayerClassType.UNKNOWN)){
                Core.getPlayerClassInventory().openPlayerClassInventory(player);
            }
        },20L);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        User u = Core.getUserManager().leaveFromGame(e.getPlayer());
        Core.getRankManager().unimplementPermissions(u);
        AbstractTablist.removeTablist(e.getPlayer());
        e.setQuitMessage(null);
    }
}
