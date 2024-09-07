package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;

public class RefreshCommand extends PlayerCommand implements Colors {
	public static HashMap<Player,Long> times = new HashMap<>();
	public RefreshCommand() {
		super("refresh", "odswieza graczy na mapie", "/refresh", "core.refresh");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Long t = times.get(p);
		if(t != null && System.currentTimeMillis() - t < 20000L)
			return Util.sendMsg(p, Util.fixColor(Util.replaceString(WarningColor + "Blad:" + WarningColor_2 + " Tej komendy mozesz uzywac co 20 sekund!")));
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(!online.hasPermission("core.vanish")) {
				p.hidePlayer(online);
				p.showPlayer(online);
			}
		}
		times.put(p, System.currentTimeMillis());
		return Util.sendMsg(p, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Odswiezono" + ImportantColor + " wszystkich graczy " + MainColor + "na serwerze!")));	
	}

}
