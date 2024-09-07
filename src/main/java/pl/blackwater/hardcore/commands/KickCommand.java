package pl.blackwater.hardcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class KickCommand extends Command implements Colors
{

	public KickCommand()
	{
		super("kick", "wyrzucanie gracza z serwera", "/kick <gracz> <powod>", "core.kick", "wyrzuc");
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
		if(args.length < 1) return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		final Player p = Bukkit.getPlayer(args[0]);
		if(p == null) return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
		if(p.hasPermission("core.kick.bypass")) return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie mozesz wyrzucic tego gracza!");
		if(p.getName().equals(sender.getName())) return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie mozesz wyrzucic samego siebie!");
		String reason = "Administrator ma zawsze racje!";
		if(args.length > 1) reason = StringUtils.join(args, " ",1,args.length);
		final String kickMsg =Util.fixColor(Util.replaceString(MessageConfig.SERVERNAME_TAG + "\n \n" + WarningColor + "             Zostales wyrzucony z serwera\n" + SpecialSigns + "» " + MainColor + "Przez: " + ImportantColor + sender.getName() + "\n" + SpecialSigns + "» " + MainColor + "Powod: " + ImportantColor + UnderLined + reason + "\n\n" + SpecialSigns + "» " + MainColor + "Kontakt TeamSpeak3:" + ImportantColor + "justpvp.pl"));
		p.kickPlayer(kickMsg);
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Gracz " + ImportantColor + p.getName() + MainColor + " zostal wyrzucony z serwera przez " + ImportantColor + sender.getName() + SpecialSigns + " (" + ImportantColor + UnderLined + reason + SpecialSigns + ")")));
		return false;
	}
	

}
