package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class CraftingCommand extends PlayerCommand {

    public CraftingCommand() {
        super("crafting", "Craftings", "/crafting", "core.crafting", "receptury","recipe","craftingi");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Core.getCustomCraftingInventory().openInventory(player);
        return true;
    }
}
