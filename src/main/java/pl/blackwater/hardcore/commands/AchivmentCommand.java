package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.inventories.AchievementInventory;
import pl.blackwaterapi.commands.PlayerCommand;

public class AchivmentCommand extends PlayerCommand {

    public AchivmentCommand() {
        super("achivment", "Achivment command", "/achivment", "core.achivment", "os","osiagniecia");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        AchievementInventory.openInventory(player);
        return false;
    }
}
