package pl.blackwater.hardcore.commands;

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

public class UnBanCommand extends Command implements Colors {

	public UnBanCommand() {
        super("unban", "Odbanowywanie graczy", "/unban <gracz>", "core.unban");
	}

	@Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 1)
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        final User user = Core.getUserManager().getUser(args[0]);
        if (user == null)
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
        final Ban b = BanManager.getBan(user);
        if (b == null)
            return Util.sendMsg(sender, WarningColor + "Blad: " + WarningColor_2 + "Ten gracz nie posiada bana");
        b.setUnban(true);
        BanManager.deleteBan(b);
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Gracz " + ImportantColor + user.getLastName() + MainColor + " zostal odbanowany " + MainColor + "przez " + ImportantColor + sender.getName())));
        return false;
    }

}
