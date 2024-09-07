package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.inventories.ChatInventory;
import pl.blackwaterapi.commands.PlayerCommand;

public class ChatCommand extends PlayerCommand{

	public ChatCommand()
	{
		super("chat", "chatmanager command", "/chat", "core.chat");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		ChatInventory.openMenu(p);
		return false;
	}

}
