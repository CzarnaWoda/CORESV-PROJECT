package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class DropCommand extends PlayerCommand {
    public DropCommand() {
        super("drop", "Komenda do drop menu", "/drop", "core.drop", "stone");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        Core.getDropInventory().openMcKoxDropMenu(player);
        return false;
    }
}
