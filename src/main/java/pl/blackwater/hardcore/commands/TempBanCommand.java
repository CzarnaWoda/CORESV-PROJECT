package pl.blackwater.hardcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Ban;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.BanManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class TempBanCommand extends Command implements Colors {

	public TempBanCommand() {
        super("tempban", "tymczasowe banowanie graczy", "/tempban <gracz> <czas> [powod]", "core.tempban");
	}

	@Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        final User user = Core.getUserManager().getUser(args[0]);
        if (user == null) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
        }
        if (user.getLastName().equalsIgnoreCase(sender.getName())) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie mozesz zbanowac sam siebie!");
        }
        if (user.getPlayer() != null && user.getPlayer().hasPermission("core.ban.bypass")) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie mozesz zbanowac tego uzytkownika!");
        }
        final String admin = sender.getName().equals("CONSOLE") ? "Console" : sender.getName();
        String reason = "Administrator ma zawsze racje!";
        reason = StringUtils.join(args, " ", 2, args.length);
        Ban ban = BanManager.getBan(user);
        if (ban != null && !ban.isAlive()) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Ten gracz ma juz bana!");
        }
        final long time = Util.parseDateDiff(args[1], true);
        BanManager.createBan(user.getUuid(), reason, admin, time);
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Gracz " + ImportantColor + user.getLastName() + MainColor + " zostal zbanowany do dnia " + WarningColor + Util.getDate(time) + MainColor + " przez" + ImportantColor + admin + SpecialSigns + " (" + ImportantColor + UnderLined + reason + SpecialSigns + ")")));
        return false;
    }

}
