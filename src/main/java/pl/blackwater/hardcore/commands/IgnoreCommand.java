package pl.blackwater.hardcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class IgnoreCommand extends PlayerCommand implements Colors {

	public IgnoreCommand() {
		super("ignore", "ignore", "ignore", "core.ignore", "wyjebane", "msgoff", "ignoreall", "zamknijmorde", "wyjebundo", "spierdalaj");
	}

	@Override
	public boolean onCommand(Player p, String[] arg1) {
		if(TellCommand.getWyjebane().contains(p.getName())) {
			TellCommand.getWyjebane().remove(p.getName());
			return Util.sendMsg(p, Util.replaceString(SpecialSigns + ">> " + ChatColor.DARK_RED + "Wylaczono" + MainColor + " ignorowanie " + ImportantColor + "prywatnych wiadomosci" + MainColor + "!"));
		}else {
			TellCommand.getWyjebane().add(p.getName());
			return Util.sendMsg(p, Util.replaceString(SpecialSigns + ">> " + ChatColor.GREEN + "Wlaczono" + MainColor + " ignorowanie " + ImportantColor + "prywatnych wiadomosci" + MainColor + "!"));
		}
	}
	

}
