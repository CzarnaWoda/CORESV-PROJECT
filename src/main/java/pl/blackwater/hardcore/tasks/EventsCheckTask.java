package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EventManager;
import pl.blackwaterapi.utils.Util;

public class EventsCheckTask extends BukkitRunnable implements Colors {
	
	@Override
	public void run() {
		for(final Player p : Bukkit.getOnlinePlayers()) {
			final User u = Core.getUserManager().getUser(p);
			if(System.currentTimeMillis() >= u.getTurboDropTime() && u.isTurboDrop()) {
				Util.sendMsg(p, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "TurboDrop " + SpecialSigns + "(" + ImportantColor +  UnderLined + "Tryb Gracz" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
				u.setTurboDrop(false);
				Core.getEasyScoreboardManager().updateEventBoard();
			}
			if(System.currentTimeMillis() >= u.getTurboExpTime() && u.isTurboExp()) {
				Util.sendMsg(p, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "TurboEXP " + SpecialSigns + "(" + ImportantColor +  UnderLined + "Tryb Gracz" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
				u.setTurboExp(false);
				Core.getEasyScoreboardManager().updateEventBoard();
			}
			if(!EventManager.isOnEventDrop()) {
				if(EventManager.getTurbodrop().containsKey(1) && System.currentTimeMillis() >= EventManager.getTurbodrop_time()) {
					Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "TurboDrop " + SpecialSigns + "(" + ImportantColor +  UnderLined + "Tryb ALL" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
					EventManager.getTurbodrop().clear();
					Core.getEasyScoreboardManager().updateEventBoard();
				}
			}
			if(!EventManager.isOnEventExp()) {
				if(EventManager.getTurboexp().containsKey(1) && System.currentTimeMillis() >= EventManager.getTurboexp_time()) {
					Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "TurboEXP " + SpecialSigns + "(" + ImportantColor +  UnderLined + "Tryb ALL" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
					EventManager.getTurboexp().clear();
					Core.getEasyScoreboardManager().updateEventBoard();
				}
			}
			if(!EventManager.isOnEventDrop()) {
				if(EventManager.getDrop().containsKey(1) && System.currentTimeMillis() >= EventManager.getDrop_time()) {
					Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "Drop x" + Config.EVENT_DROP + SpecialSigns + " (" + ImportantColor +  UnderLined + "Tryb ALL" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
					EventManager.getDrop().clear();
					Core.getEasyScoreboardManager().updateEventBoard();
				}
			}
			if(!EventManager.isOnEventExp()) {
				if(EventManager.getExp().containsKey(1) && System.currentTimeMillis() >= EventManager.getExp_time()) {
					Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Czas eventu " + ImportantColor + "EXP x" + Config.EVENT_EXP + SpecialSigns + " (" + ImportantColor +  UnderLined + "Tryb ALL" + SpecialSigns + ") " + MainColor + "skonczyl sie!")));
					EventManager.getExp().clear();
					Core.getEasyScoreboardManager().updateEventBoard();
				}
			}
		}
	}

}
