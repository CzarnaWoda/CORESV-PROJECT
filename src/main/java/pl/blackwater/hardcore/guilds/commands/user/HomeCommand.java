package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.timer.TimerUtil;
import pl.blackwaterapi.utils.Util;

public class HomeCommand extends PlayerCommand implements Colors {
    public HomeCommand() {
        super("baza", "Teleportacja na baze gildii", "/g baza", "guild.main", "home");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        TimerUtil.teleport(player,guild.getHomeLocation(),10);
        return Util.sendMsg(player, guildPrefix + "Teleportacja na baze gildii nastapi za " + ImportantColor + "10 sekund");
    }
}
