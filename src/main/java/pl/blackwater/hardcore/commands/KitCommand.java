package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.inventories.KitInventory;
import pl.blackwaterapi.commands.PlayerCommand;

public class KitCommand extends PlayerCommand {
    public KitCommand() {
        super("kit", "Kit Command", "/kit", "core.kit", "zestaw","kits");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        KitInventory.openKitInventory(player);
        return false;
    }
}
