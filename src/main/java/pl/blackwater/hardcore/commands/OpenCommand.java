package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.Util;

public class OpenCommand extends PlayerCommand implements Colors
{

	public OpenCommand()
	{
		super("open", "otwiera ekwipunek gracza", "/open [armor/inventory/enderchest] <player>", "core.open");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if(args.length != 2) 
			return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		Player o = Bukkit.getPlayer(args[1]);
		if(o == null) {
			return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
		}
		if(args[0].equalsIgnoreCase("armor") || args[0].equalsIgnoreCase("zbroja"))
		{
			InventoryGUI inventory = new InventoryGUI(Core.getInstance(), MainColor + "Zbroja gracza " + ImportantColor + UnderLined + o.getName(), 1);
			inventory.setItem(0, o.getInventory().getHelmet(), null);
			inventory.setItem(1, o.getInventory().getChestplate(), null);
			inventory.setItem(2, o.getInventory().getLeggings(), null);
			inventory.setItem(3, o.getInventory().getBoots(), null);
			inventory.openInventory(p);
		}else if (args[0].equalsIgnoreCase("inventory") || args[0].equalsIgnoreCase("ekwipunek"))
		{
			p.openInventory(o.getInventory());
		}else if (args[0].equalsIgnoreCase("enderchest") || args[0].equalsIgnoreCase("ender"))
		{
			p.openInventory(o.getEnderChest());
		}else return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		return false;
	}

}
