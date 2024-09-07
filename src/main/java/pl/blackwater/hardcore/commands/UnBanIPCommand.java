package pl.blackwater.hardcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.BanIP;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.BanIPManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class UnBanIPCommand extends Command implements Colors {

	public UnBanIPCommand() {
        super("unbanip", "odbanowywanie adresow ip", "/unban <gracz/adres IP>", "core.unbanip");
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        User u = Core.getUserManager().getUser(args[0]);
        String ip = args[0];
        if (u != null) {
            ip = u.getLastIP();
        }
        BanIP b = BanIPManager.getBanIP(ip);
        if (b == null) {
            return Util.sendMsg(sender, WarningColor + "Blad: " + WarningColor_2 + "Ten adres IP nie posiada bana");
        }
        b.setUnban(true);
        BanIPManager.deleteBan(b);
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + (u != null ? "Gracz " : "Adres IP ") + ImportantColor + (u != null ? u.getLastName() : ChatColor.MAGIC + ip) + MainColor + " zostal odbanowany na " + ImportantColor + BOLD + "IP " + MainColor + "przez " + ImportantColor + sender.getName())));
        return false;
	}

}
