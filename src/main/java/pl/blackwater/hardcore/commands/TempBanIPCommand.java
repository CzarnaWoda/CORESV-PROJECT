package pl.blackwater.hardcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
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

public class TempBanIPCommand extends Command implements Colors {

    public TempBanIPCommand() {
        super("tempbanip", "tymczasowe banowanie adresow IP", "/tempban <gracz/adres IP> <czas> [powod]", "core.tempbanip");
	}
	@Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        final User user = Core.getUserManager().getUser(args[0]);
        String ip = args[0];
        if (user != null) {
            ip = user.getLastIP();
        }
        final String admin = sender.getName().equals("CONSOLE") ? "Console" : sender.getName();
        String reason = "Administrator ma zawsze racje!";
        reason = StringUtils.join(args, " ", 2, args.length);
        BanIP ban = BanIPManager.getBanIP(ip);
        if (ban != null && !ban.isAlive()) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Ten adres IP ma juz bana!");
        }
        final long time = Util.parseDateDiff(args[1], true);
        BanIPManager.createBan(ip, reason, admin, time);
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + (user != null ? "Gracz " : "Adres IP ") + ImportantColor + (user != null ? user.getLastName() : ChatColor.MAGIC + ip) + MainColor + " zostal zbanowany na " + ImportantColor + BOLD + "IP " + MainColor + "do dnia " + WarningColor + Util.getDate(time) + MainColor + " przez " + ImportantColor + admin + SpecialSigns + " (" + ImportantColor + UnderLined + reason + SpecialSigns + ")")));
        return false;
    }

}
