package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class EffectShopCommand extends PlayerCommand {
    public EffectShopCommand() {
        super("effect", "Sklep z efektami", "/effect", "core.effect", "efekty","efekt");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        Core.getShopInventory().openEffect(player);
        return false;
    }
}
