package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.commands.PlayerCommand;

public class ServerManagerCommand extends PlayerCommand {
    public ServerManagerCommand() {
        super("servermanager", "sm", "/servermanager", "core.servermanager", "sm","panel","adminpanel");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        Core.getServerManagerInventory().openMenu(player);
        return false;
    }
}
