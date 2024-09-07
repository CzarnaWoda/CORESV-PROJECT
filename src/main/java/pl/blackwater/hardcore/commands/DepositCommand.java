package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.inventories.DepositInventory;
import pl.blackwaterapi.commands.PlayerCommand;

public class DepositCommand extends PlayerCommand {
    public DepositCommand() {
        super("deposit", "Deposit command", "/deposit", "core.deposit", "depozyt","schowek");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        DepositInventory.openDepositMenu(player);
        return false;
    }
}
