package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EventManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCommand extends Command implements Colors {

	public EventCommand() {
		super("event", "zarzadzanie eventami na serwerze", "/event <turbomoney/turbodrop/turboexp> <gracz/gildia/graczremove> <gracz/gildia> [time] || /event <turbomoney/turbodrop/turboexp> <all> [time] || /event <drop/exp/infinitystone> <amount> [time]" , "core.event");
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
		if(args.length < 3)
			return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		if(args[0].equalsIgnoreCase("turbodrop")) {
			if(args[1].equalsIgnoreCase("all")) {
				EventManager.setTurbodrop_admin(sender.getName());
				final long eventtime = Util.parseDateDiff(args[2], true);
				EventManager.setTurbodrop_time(eventtime);
				final Date d = new Date(eventtime);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String timeShow = sdf.format(d);
				EventManager.getTurbodrop().put(1, "");
				Core.getEasyScoreboardManager().updateEventBoard();
				Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Administrator " + ImportantColor + sender.getName() + MainColor + " aktywowal event TurboDrop do " + SpecialSigns + "(" + ImportantColor + timeShow  + SpecialSigns + ")")));
			}else if (args[1].equalsIgnoreCase("gracz")) {
				if(args.length == 4) {
					final Player o = Bukkit.getPlayer(args[2]);
					if(o == null) {
						return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
					}
					final User u = Core.getUserManager().getUser(o);
					if(u.isTurboDrop())
						return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Ten gracz posiada juz turbodrop, wpisz /event turbodrop graczremove <gracz> aby anulowac event turbodrop!")));
					final long eventtime = Util.parseDateDiff(args[3], true);
					u.setTurboDrop(true);
					u.setTurboDropTime(eventtime);
					return eventtime == 0L ? Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano graczowi " + ImportantColor + u.getLastName() + MainColor + " usluge TurboDrop na " + ImportantColor + "zawsze"))) : Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano graczowi " + ImportantColor + u.getLastName() + MainColor +  " usluge TurboDrop do dnia " + ImportantColor + Util.getDate(eventtime))));		
				}else
					return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
			}else if (args[1].equalsIgnoreCase("graczremove")) {
				final Player o = Bukkit.getPlayer(args[2]);
				if(o == null) {
					return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
				}
				final User u = Core.getUserManager().getUser(o);
				if(!u.isTurboDrop())
					return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Ten gracz nie posiada turbodrop, wpisz /event turbodrop gracz <gracz> [time] aby nadac event turbodrop!")));
				u.setTurboDrop(false);
				return Util.sendMsg(sender, Util.replaceString(SpecialSigns + ">> " + MainColor + "Zabrano usluge TurboDrop dla gracza " + ImportantColor + u.getLastName()));
			}else if (args[1].equalsIgnoreCase("gildia")) {
				/*if(args.length == 4) {
					final Guild g = GuildManager.getGuild(args[2]);
					if(g == null)
						Util.sendMsg(sender, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Gildia o takim tagu/nazwie nie istnieje!"));
					final long eventtime = Util.parseDateDiff(args[3], true);
					for(Member m : MemberManager.getGuildMembers(g)) {
						final User u = UserManager.getUser(m.getPlayer());
						u.setTurboDrop(true);
						u.setTurboDropTime(eventtime);
					}
					assert g != null;
					return eventtime == 0L ? Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano gildii " + ImportantColor + g.getTag() + MainColor + " usluge TurboDrop na " + ImportantColor + "zawsze"))) : Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano gildii " + ImportantColor + g.getTag() + MainColor +  " usluge TurboDrop do dnia " + ImportantColor + Util.getDate(eventtime))));
				}*/
			}else
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		}else if(args[0].equalsIgnoreCase("turboexp")) {
			if(args[1].equalsIgnoreCase("all")) {
				EventManager.setTurboexp_admin(sender.getName());
				final long eventtime = Util.parseDateDiff(args[2], true);
				EventManager.setTurboexp_time(eventtime);
				final Date d = new Date(eventtime);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String timeShow = sdf.format(d);
				EventManager.getTurboexp().put(1, "");
				Core.getEasyScoreboardManager().updateEventBoard();
				Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Administrator " + ImportantColor + sender.getName() + MainColor + " aktywowal event TurboEXP do " + SpecialSigns + "(" + ImportantColor + timeShow  + SpecialSigns + ")")));
			}else if (args[1].equalsIgnoreCase("gracz")) {
				if(args.length == 4) {
					final Player o = Bukkit.getPlayer(args[2]);
					if(o == null) {
						return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
					}
					final User u = Core.getUserManager().getUser(o);
					if(u.isTurboExp())
						return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Ten gracz posiada juz turboEXP, wpisz /event turboexp graczremove <gracz> aby anulowac event turboEXP!")));
					final long eventtime = Util.parseDateDiff(args[3], true);
					u.setTurboExp(true);
					u.setTurboExpTime(eventtime);
					return eventtime == 0L ? Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano graczowi " + ImportantColor + u.getLastName() + MainColor + " usluge TurboEXP na " + ImportantColor + "zawsze"))) : Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano graczowi " + ImportantColor + u.getLastName() + MainColor +  " usluge TurboEXP do dnia " + ImportantColor + Util.getDate(eventtime))));		
				}else
					return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
			}else if (args[1].equalsIgnoreCase("graczremove")) {
				final Player o = Bukkit.getPlayer(args[2]);
				if(o == null) {
					return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
				}
				final User u = Core.getUserManager().getUser(o);
				if(!u.isTurboExp())
					return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Ten gracz nie posiada turboexp, wpisz /event turboexp gracz <gracz> [time] aby nadac event turboexp!")));
				u.setTurboExp(false);
				return Util.sendMsg(sender, Util.replaceString(SpecialSigns + ">> " + MainColor + "Zabrano usluge TurboEXP dla gracza " + ImportantColor + u.getLastName()));
			}else if (args[1].equalsIgnoreCase("gildia")) {
				/*if(args.length == 4) {
					final Guild g = GuildManager.getGuild(args[2]);
					if(g == null)
						Util.sendMsg(sender, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Gildia o takim tagu/nazwie nie istnieje!"));
					final long eventtime = Util.parseDateDiff(args[3], true);
					for(Member m : MemberManager.getGuildMembers(g)) {
						final User u = UserManager.getUser(m.getPlayer());
						u.setTurboExp(true);
						u.setTurboExpTime(eventtime);
					}
					assert g != null;
					return eventtime == 0L ? Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano gildii " + ImportantColor + g.getTag() + MainColor + " usluge TurboEXP na " + ImportantColor + "zawsze"))) : Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Dano gildii " + ImportantColor + g.getTag() + MainColor +  " usluge TurboEXP do dnia " + ImportantColor + Util.getDate(eventtime))));
				}*/
			}else
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		}else if(args[0].equalsIgnoreCase("drop")) {
			if(Util.isInteger(args[1])) {
				final int amount = Config.EVENT_DROP = Integer.parseInt(args[1]);
				Core.getDropConfig().setField("event.drop",amount);
				EventManager.setDrop_admin(sender.getName());
				final long eventtime = Util.parseDateDiff(args[2], true);
				EventManager.setDrop_time(eventtime);
				final Date d = new Date(eventtime);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String timeShow = sdf.format(d);
				EventManager.getDrop().put(1, "");
				Core.getEasyScoreboardManager().updateEventBoard();
				Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Administrator " + ImportantColor + sender.getName() + MainColor + " aktywowal event Drop x" + ImportantColor + amount + MainColor + " do " + SpecialSigns + "(" + ImportantColor + timeShow  + SpecialSigns + ")")));
			}else
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		}else if(args[0].equalsIgnoreCase("exp")) {
			if(Util.isInteger(args[1])) {
				final int amount = Config.EVENT_EXP = Integer.parseInt(args[1]);
				Core.getDropConfig().setField("event.exp",amount);
				EventManager.setExp_admin(sender.getName());
				final long eventtime = Util.parseDateDiff(args[2], true);
				EventManager.setExp_time(eventtime);
				final Date d = new Date(eventtime);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				String timeShow = sdf.format(d);
				EventManager.getExp().put(1, "");
				Core.getEasyScoreboardManager().updateEventBoard();
				Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Administrator " + ImportantColor + sender.getName() + MainColor + " aktywowal event EXP x" + ImportantColor + amount + MainColor + " do " + SpecialSigns + "(" + ImportantColor + timeShow  + SpecialSigns + ")")));
			}else
				return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
		}
		return false;
	}
}
