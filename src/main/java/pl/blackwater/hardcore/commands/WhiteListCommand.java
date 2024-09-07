package pl.blackwater.hardcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.WhiteListManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class WhiteListCommand extends Command implements Colors {

	public WhiteListCommand() {
		super("whitelist", "whitelist serwer'a", "/whitelist <on/off/add/remove/list> [gracz]", "core.whitelist", "wl");
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
		if(args.length < 1)
			return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		switch (args[0]) {
		case "remove":
			if(args.length != 2)
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
			final User user = Core.getUserManager().getUser(args[1]);
			if(user == null){
				return Util.sendMsg(sender,MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
			}
			if(!WhiteListManager.isWhitelist(user.getUuid())) {
				return Util.sendMsg(sender, WarningColor + "Blad:" + WarningColor_2 + " Ten gracz nie jest na whitelist!");
			}
			WhiteListManager.removeWhitelist(user);
			return Util.sendMsg(sender, MainColor + "Usunieto " + ImportantColor + args[1] + MainColor + " z whitelist!");
		case "on":
			WhiteListManager.setWhitelist(true);
			return Util.sendMsg(sender, MainColor + "Poprawnie" + ChatColor.GREEN + " wlaczono" + MainColor + " whitelist!");
		case "off":
			WhiteListManager.setWhitelist(false);
			return Util.sendMsg(sender, MainColor + "Poprawnie" + ChatColor.DARK_RED + " wylaczono" + MainColor + " whitelist!");
		case "add":
			if(args.length != 2)
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
			final User user2 = Core.getUserManager().getUser(args[1]);
			if(user2 == null){
				return Util.sendMsg(sender,MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
			}
			if(WhiteListManager.isWhitelist(user2.getUuid()))
				return Util.sendMsg(sender, WarningColor + "Blad:" + WarningColor_2 + " Ten gracz jest juz na whitelist!");
			WhiteListManager.addWhitelist(user2);
			return Util.sendMsg(sender, MainColor + "Dodano " + ImportantColor + args[1] + MainColor + " do whitelist!");
		case "list":
			return Util.sendMsg(sender, MainColor + "Lista graczy na whitelist: " + ImportantColor + StringUtils.join(WhiteListManager.getWhitelistedNames(), MainColor + "," + ImportantColor + " "));
		default:
			break;
		}
		return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
	}


	

}
