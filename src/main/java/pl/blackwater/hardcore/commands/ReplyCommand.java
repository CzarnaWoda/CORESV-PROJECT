package pl.blackwater.hardcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

import java.util.UUID;

public class ReplyCommand extends PlayerCommand implements Colors
{
    public ReplyCommand() {
        super("reply", "odpowiedz na prywatna wiadomosc", "/reply <wiadomosc>", "core.reply", "r");
    }
    
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 1) {
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        final UUID last = TellCommand.getLastMsg().get(player.getUniqueId());
        if (last == null) {
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie masz komu odpisac!");
        }
        final Player o = Bukkit.getPlayer(last);
        if (o == null) {
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
        }
        final String message = ChatColor.stripColor(Util.fixColor(StringUtils.join(args, " ")));
        TellCommand.getLastMsg().put(player.getUniqueId(), o.getUniqueId());
        TellCommand.getLastMsg().put(o.getUniqueId(), player.getUniqueId());
        Util.sendMsg(player, ImportantColor + "Ja" + MainColor + " -> " + ImportantColor + o.getName() + MainColor + ": " + ImportantColor + message);
        for (final Player plr : Bukkit.getOnlinePlayers()){
        	//if (AdminPanelCommand.getSS_ADMINS().contains(plr)){
        	//	plr.sendMessage(Util.fixColor(SpecialSigns + "[" + WarningColor + "Szpieg" + SpecialSigns + "]" + WarningColor_2 + player.getName() + " -> " + o.getName() + ": " + message));
        	//}
        }
        return Util.sendMsg(o, ImportantColor + player.getName() + MainColor + " -> " + ImportantColor + "Ja" + MainColor + ": " + ImportantColor + message);
        }
    }
