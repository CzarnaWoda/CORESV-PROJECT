package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class FlyCommand extends PlayerCommand implements Colors
{
    public FlyCommand() {
        super("fly", "zarzadzanie trybem latania graczy", "/fly [gracz]", "core.fly");
    }
    
    public boolean onCommand(Player p, String[] args) {
        if (args.length == 1) {
            if (!p.hasPermission("core.fly.others")) {
                return Util.sendMsg(p, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie posiadasz uprawnien do tej komendy " + SpecialSigns + "(" + WarningColor + "core.fly.others" + SpecialSigns + ")");
            }
            Player o = Bukkit.getPlayer(args[0]);
            if (o == null) {
                return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
            }
            User u = Core.getUserManager().getUser(o);
            if (u == null) {
                return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
            }
            o.setAllowFlight(!o.getAllowFlight());
            u.setFly(!u.isFly());
            Util.sendMsg(p, ImportantColor + "" + (u.isFly() ? "Wlaczono" : "Wylaczono") + MainColor + " tryb latania dla gracza " + ImportantColor + u.getLastName() + MainColor + "!");
            return Util.sendMsg(o, ImportantColor + "" + (u.isFly() ? "Wlaczono" : "Wylaczono") + MainColor + " tryb latania przez " + ImportantColor + p.getName() + MainColor + "!");
        }
        else {
            User u2 = Core.getUserManager().getUser(p);
            if (u2 == null) {
                return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
            }
            p.setAllowFlight(!p.getAllowFlight());
            u2.setFly(!u2.isFly());
            return Util.sendMsg(p, ImportantColor + "" + (u2.isFly() ? "Wlaczono" : "Wylaczono") + MainColor + " tryb latania!");
        }
    }
}
