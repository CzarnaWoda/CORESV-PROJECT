package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class ShopCommand extends PlayerCommand {
    public ShopCommand() {
        super("shop", "Shop Command", "/shop", "core.shop", "sklep");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        Core.getShopInventory().openMain(player);
        return false;
    }
}
