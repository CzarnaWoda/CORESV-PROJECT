package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class DeleteCommand extends PlayerCommand implements Colors {
    public DeleteCommand() {
        super("usun", "Usuwa gildie", "/g usun", "guild.main", "delete");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        if(!guild.getOwner().equals(player.getUniqueId())){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie jestes zalozycielem gildii");
        }
        if(!guild.isPreDeleted()){
            guild.setPreDeleted(true);
            Util.sendMsg(player, WarningColor + "Aby usunac gildie musisz ponownie wpisac " + WarningColor_2 + "/g usun");
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> guild.setPreDeleted(false),200L);
            return true;
        }
        Core.getGuildManager().removeGuild(guild);
        Bukkit.broadcastMessage(guildPrefix + "Gracz " + ImportantColor + player.getDisplayName() + MainColor + " usunal gildie " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
        return Util.sendMsg(player, Util.fixColor(Util.replaceString(guildPrefix + "Poprawnie usunales " + ImportantColor + "swoja gildie")));
    }
}
