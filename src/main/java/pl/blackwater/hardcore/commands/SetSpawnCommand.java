package pl.blackwater.hardcore.commands;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class SetSpawnCommand extends PlayerCommand implements Colors {

    public SetSpawnCommand() {
        super("setspawn", "Setting spawn", "/setspawn", "core.setspawn");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        return Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + MainColor + "Ustawiono nowy spawn na koordynatach: " + ImportantColor + "x: " + player.getLocation().getBlockX() + " y: " + player.getLocation().getBlockY() + " z: " + player.getLocation().getBlockZ()));
    }
}
