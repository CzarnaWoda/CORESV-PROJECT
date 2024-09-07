package pl.blackwater.hardcore.commands;

import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class RankingSetCommand extends Command implements Colors {
    public RankingSetCommand() {
        super("rankingset", "Set new values on user ranking", "/rankingset [user] [money|exp|level|kills|deaths|logouts|assists|points|depositRef|depositKox|depositPearl|eatedRef|eatedKox|throwedPearl|bonusDrop] [value]", "core.rankingset", "setranking");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length != 3){
            return Util.sendMsg(sender,MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        final User user = Core.getUserManager().getUser(args[0]);
        if(user == null){
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
        }
        if(!Util.isInteger(args[2])){
            return Util.sendMsg(sender, WarningColor + "Blad: " + WarningColor_2 + "Podana wartosc nie jest liczba");
        }
        final int value = Integer.parseInt(args[2]);
        final String message = Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Ustawiono wartosc " + ImportantColor + args[1].toUpperCase() + MainColor + " dla gracza " + ImportantColor + user.getLastName() + MainColor + " na  " + ImportantColor + value));
        switch (args[1]) {
            case "money":
                user.setCoins(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "exp":
                user.setExp(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "level":
                user.setLevel(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "kills":
                user.setKills(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "deaths":
                user.setDeaths(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "logouts":
                user.setLogouts(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "assists":
                user.setAssists(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "points":
                user.setPoints(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "depositKox":
                user.setDepositKox(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "depositRef":
                user.setDepositRef(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "depositPearl":
                user.setDepositPearl(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "eatedRef":
                user.setEatedRef(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "eatedKox":
                user.setEatedKox(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "throwedPearl":
                user.setThrowedPearl(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            case "bonusDrop":
                user.setBonusDrop(value);
                user.update(false);
                return Util.sendMsg(sender, message);
            default:
                return Util.sendMsg(sender,MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }

    }
}
