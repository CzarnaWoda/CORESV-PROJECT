package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class PvPCommand extends Command implements Colors
{

	public PvPCommand()
	{
		super("worldpvp", "worldpvp", "/worldpvp", "core.worldpvp", "wpvp");
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
		World w = Bukkit.getWorld("world");
		w.setPVP(!w.getPVP());
		String message = Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + " PvP na mapie zostalo " + (w.getPVP() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone") + MainColor + " przez " + ImportantColor + sender.getName()));
		Bukkit.broadcastMessage(message);
		Util.sendMsg(sender, Util.replaceString(SpecialSigns + ">> " + MainColor + "PvP na mapie " + ImportantColor + UnderLined + "world" + MainColor + " zostalo " + (w.getPVP() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone")));
		return false;
	}

}
