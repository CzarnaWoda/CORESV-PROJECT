package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class GiftCommand extends PlayerCommand implements Colors {

	public GiftCommand() {
		super("gift", "dawanie", "/gift [nazwa skrzynii] [ilosc]", "core.gift");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if(args.length < 2){
			return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		}
	        int value = Integer.parseInt(args[1]);
	        if (!Util.isInteger(args[1])) {
	            return Util.sendMsg(p, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Podana wartosc nie jest liczba!");
	        }
	        if (value < 0) {
	            return Util.sendMsg(p, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Podana wartosc nie jest liczba!");
	        }
	        if (args.length == 2){
	        	Bukkit.broadcastMessage(Util.fixColor("          "+ MessageConfig.SERVERNAME_TAG + "          "));
	        	Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Wszyscy Gracze Dostali Klucze Do Skrzynii")));
	        	Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Nazwa Skrzyni: " + ImportantColor + args[0] + " " + SpecialSigns + "(" + ImportantColor + args[1] + " " + MainColor + "szt. " + SpecialSigns + ")")));
	        	Bukkit.broadcastMessage(Util.fixColor("          "+ MessageConfig.SERVERNAME_TAG + "          "));
	        	for (Player giftp : Bukkit.getOnlinePlayers()){
	        		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "case givekey " + giftp.getDisplayName() + " " + args[0] + " " + args[1]);
	        	}
	        }
		return false;
	}

}
