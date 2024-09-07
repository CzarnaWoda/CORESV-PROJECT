package pl.blackwater.hardcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class SideBarCommand extends PlayerCommand implements Colors {

	public SideBarCommand() {
		super("sidebar", "/sidebar", "/sidebar", "core.sidebar");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if(Core.getEasyScoreboardManager().getScoreboard(p) == null)
			Core.getEasyScoreboardManager().createScoreBoard(p);
		else
			Core.getEasyScoreboardManager().removeScoreBoard(p);
		return Util.sendMsg(p, Util.replaceString(SpecialSigns + ">> " + (Core.getEasyScoreboardManager().getScoreboard(p) != null ? ChatColor.GREEN + "Wlaczono" : ChatColor.DARK_RED + "Wylaczono") + MainColor + " sidebar!"));
	}
}
