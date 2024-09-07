package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.timer.TimerUtil;

public class SpawnCommand extends PlayerCommand {

    public SpawnCommand() {
        super("spawn", "Teleport to spawn", "/spawn", "core.spawn");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        TimerUtil.teleport(player, player.getWorld().getSpawnLocation(), 10);
        return false;
    }
}
