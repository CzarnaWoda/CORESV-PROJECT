package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.EasyScoreboard;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;

public class EasyScoreboardManager implements Colors {
    @Getter private HashMap<String, EasyScoreboard> scoreboards = new HashMap<>();
    @Getter private HashMap<Integer, String> lines = new HashMap<>();

    public EasyScoreboard getScoreboard(final Player player){
        return scoreboards.get(player.getName());
    }
    public void removeScoreBoard(final Player player){
    	if(scoreboards.get(player.getName()) != null){
    	scoreboards.remove(player.getName());
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    	}
    }
    public void createScoreBoard(final Player p){
    	final EasyScoreboard sidebar = new EasyScoreboard(p, Util.fixColor(Util.replaceString("&8->> &6&l&nEVENTY&8 <<-")));
    	final User u = Core.getUserManager().getUser(p);
    	if(u.isTurboDrop()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(u.getTurboDropTime())));
			sidebar.addLine(Util.replaceString("&8|->> &aTURBODROP &8(&4GRACZ&8)"));
		}
		if(u.isTurboExp()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(u.getTurboExpTime())));
			sidebar.addLine(Util.replaceString("&8|->> &aTURBOEXP &8(&4GRACZ&8)"));
		}
		if(EventManager.isOnTurboDrop()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(EventManager.getTurbodrop_time())));
			sidebar.addLine(Util.replaceString("&8|->> &aTURBODROP &8(&4ALL&8)"));
		}
		if(EventManager.isOnTurboExp()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(EventManager.getTurboexp_time())));
			sidebar.addLine(Util.replaceString("&8|->> &aTURBOEXP &8(&4ALL&8)"));
		}
		if(EventManager.isOnEventDrop()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(EventManager.getDrop_time())));
			sidebar.addLine(Util.replaceString("&8|->> &aDROP x" + Config.EVENT_DROP));
		}
		if(EventManager.isOnEventExp()){
			sidebar.addLine(Util.replaceString("&8  * &7Aktywny do: &e" + Util.getTime(EventManager.getExp_time())));
			sidebar.addLine(Util.replaceString("&8|->> &aEXP x" + Config.EVENT_EXP));
		}
		sidebar.addBlankLine();
		if(sidebar.scores.size() < 2){
			removeScoreBoard(p);
		}
	}
	public void updateEventBoard(){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(getScoreboard(p) != null) {
				Core.getEasyScoreboardManager().removeScoreBoard(p);
			}
			Core.getEasyScoreboardManager().createScoreBoard(p);
		}
	}
}