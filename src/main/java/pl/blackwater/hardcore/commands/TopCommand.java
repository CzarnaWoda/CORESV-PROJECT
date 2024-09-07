package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class TopCommand extends PlayerCommand {
    public TopCommand() {
        super("top", "top", "/top", "core.top", "topki");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        Core.getTopInventory().openInventory(player);
        return false;
    }
}
