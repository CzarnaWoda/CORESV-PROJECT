package pl.blackwater.hardcore.commands;

import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class RankingResetCommand extends Command implements Colors {

    public RankingResetCommand() {
        super("rankingreset", "Resets ranking values", "/rankingset [user] [stats|allDatas|withEnder|all|playerClass]", "core.rankingset", "setranking");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length != 3){
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        final User user = Core.getUserManager().getUser(args[0]);
        if(user == null){
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
        }
        user.resetRanking(args[1]);
        return Util.sendMsg(sender, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zresetowano " + ImportantColor + args[1].replace("stats", "statystyki").replace("allDatas", "wszystkie statystyki").replace("withEnder", "wszystkie statystyki z enderchestem").replace("all", "wszystko").replace("playerClass", "klase") + MainColor + " dla gracza " + ImportantColor + user.getLastName())));
    }
}
