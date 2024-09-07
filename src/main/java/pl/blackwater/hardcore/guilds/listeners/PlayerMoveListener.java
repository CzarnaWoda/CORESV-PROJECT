package pl.blackwater.hardcore.guilds.listeners;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.events.PlayerEnterGuildTerrainEvent;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;

public class PlayerMoveListener implements Colors, Listener {
    @Getter
    private static final HashMap<Guild, Long> alertTimes = new HashMap<>();
    @Getter
    private static final HashMap<Player, Long> enterTimes = new HashMap<>();
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e){
        final int xfrom = e.getFrom().getBlockX();
        final int zfrom = e.getFrom().getBlockZ();
        final int yfrom = e.getFrom().getBlockY();
        final int xto = e.getTo().getBlockX();
        final int yto = e.getTo().getBlockY();
        final int zto = e.getTo().getBlockZ();
        if (xfrom != xto || zfrom != zto || yfrom != yto) {
            final Player p = e.getPlayer();
            final Guild from = Core.getGuildManager().getGuild(e.getFrom());
            final Guild to = Core.getGuildManager().getGuild(e.getTo());
            final Guild g = Core.getGuildManager().getGuild(p);
            if (from == null && to != null) {
                Bukkit.getPluginManager().callEvent(new PlayerEnterGuildTerrainEvent(to,p,e.getTo()));
            }else if (from != null && to == null){
                Util.sendMsg(p, guildPrefix + WarningColor + "Opusciles teren gildii, jestes na neutralnym terytorium");
            }
        }
    }
    @EventHandler
    public void onPlayerEnteredGuild(PlayerEnterGuildTerrainEvent e){
        final Player player = e.getWhoEntered();
        final Long enterTime = getEnterTimes().get(player);
        if(enterTime == null || System.currentTimeMillis() - enterTime >= 5000L) {
            Util.sendMsg(player, guildPrefix + WarningColor + "Wszedles na terytorium gildii " + SpecialSigns + "[" + WarningColor_2 + e.getEnteredGuild().getTag() + SpecialSigns + "]" + WarningColor_2 + e.getEnteredGuild().getName());
            getEnterTimes().put(player, System.currentTimeMillis());
        }
        if(e.getEnteredGuild().getCreateTime() + TimeUtil.HOUR.getTime(24) > System.currentTimeMillis()){
            Util.sendMsg(player, guildPrefix + WarningColor + "Gildia posiada 24h ochrone podczas ktorej nie moze wybuchnac TNT, pozostaly czas ochrony: " + WarningColor_2  + Util.secondsToString((int)((e.getEnteredGuild().getCreateTime() + TimeUtil.HOUR.getTime(24)) - System.currentTimeMillis())/1000));
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!player.isOp() && !player.hasPermission("guild.bypass") && member != null && !Core.getMemberManager().getMembers(e.getEnteredGuild()).contains(member)){
            final Long time = getAlertTimes().get(e.getEnteredGuild());
            if(time == null || System.currentTimeMillis() - time >= 30000L) {
                for (Member members : Core.getMemberManager().getOnlineMembers(e.getEnteredGuild())) {
                    Util.sendMsg(members.getPlayer(), guildPrefix + WarningColor + "Uwaga, intruz na terenie twojej gildii");
                }
                getAlertTimes().put(e.getEnteredGuild(), System.currentTimeMillis());
            }
        }
    }
}
