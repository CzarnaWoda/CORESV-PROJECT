package pl.blackwater.hardcore;

import org.bukkit.entity.Player;
import pl.blackwaterapi.commands.PlayerCommand;

public class debugcommand extends PlayerCommand {
    public debugcommand() {
        super("debug", "d", "", "debug");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        return false;
    }
}
