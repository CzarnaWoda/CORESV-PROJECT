package pl.blackwater.hardcore.guilds.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;

public class GuildChatListener implements Listener, Colors {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        final String message = e.getMessage();
        final Player p = e.getPlayer();
        final Guild guild = Core.getGuildManager().getGuild(p);
        if(guild == null){
            return;
        }
        if(message.startsWith("!!")){
            e.setCancelled(true);
            final String msg = message.replaceAll("!","").replaceAll("&","");
            guild.sendMessage(SpecialSigns + "[" + ChatColor.GOLD + "DO SOJUSZY" + SpecialSigns + "]" + ChatColor.GOLD + ": " + ChatColor.YELLOW + msg);
            for(Guild guilds : Core.getAllianceManager().getAlliancesAsGuilds(guild)){
                guilds.sendMessage(SpecialSigns + "[" + ChatColor.GOLD + "DO SOJUSZY" + SpecialSigns + "]" + ChatColor.GOLD + ": " + ChatColor.YELLOW + msg);
            }
        }else if (message.startsWith("!")){
            e.setCancelled(true);
            final String msg = message.replaceAll("!","").replaceAll("&","");
            guild.sendMessage(SpecialSigns + "[" + ChatColor.DARK_GREEN + "DO GILDII" + SpecialSigns + "]" + ChatColor.DARK_GREEN + ": " + ChatColor.GREEN + msg);
        }
    }
}
