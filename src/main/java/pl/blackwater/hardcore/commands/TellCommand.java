package pl.blackwater.hardcore.commands;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TellCommand extends PlayerCommand implements Colors
{
    @Getter private final static HashMap<UUID, UUID> lastMsg  = new HashMap<>();
    @Getter private final static List<String> wyjebane = new ArrayList<>();
    
    public TellCommand() {
        super("tell", "prywatne wiadomosci do graczy", "/tell <gracz> <wiadomosc>", "core.tell", "whisper", "t", "m", "msg");
    }
    
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) {
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        final Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
        }
        if (o.hasPermission("core.tell.nomsg") && !player.hasPermission("core.tell.nomsg")) {
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz wysylac wiadomosci do tego gracza!");
        }
        if (wyjebane.contains(o.getName()) && !player.hasPermission("core.tell.bypass")){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Ten gracz ignoruje prywatne wiadomosci!");
        }
        final String message = ChatColor.stripColor(Util.fixColor(StringUtils.join(args, " ", 1, args.length)));
        TellCommand.lastMsg.put(player.getUniqueId(), o.getUniqueId());
        TellCommand.lastMsg.put(o.getUniqueId(), player.getUniqueId());
        Util.sendMsg(player, ImportantColor + "Ja" + MainColor + " -> " + ImportantColor + o.getName() + MainColor + ": " + ImportantColor + message);
        for (final Player plr : Bukkit.getOnlinePlayers()){
        	//if (AdminPanelCommand.getSS_ADMINS().contains(plr)){
        	//	plr.sendMessage(Util.fixColor(SpecialSigns + "[" + WarningColor + "Szpieg" + SpecialSigns + "]" + WarningColor_2 + player.getName() + " -> " + o.getName() + ": " + message));
        	//}
        }
        return Util.sendMsg(o, ImportantColor + player.getName() + MainColor + " -> " + ImportantColor + "Ja" + MainColor + ": " + ImportantColor + message);
    }
}
