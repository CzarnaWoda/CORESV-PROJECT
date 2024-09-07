package pl.blackwater.hardcore.commands.ranks;

import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class CreateCommand extends Command implements Colors {
    public CreateCommand() {
        super("create", "Tworzy nową range!", "/rank create [rank]", "core.rank");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length != 1){
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        Rank rank = Core.getRankManager().getRank(args[0]);
        if(rank == null){
            Core.getRankManager().createRank(args[0]);
            return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor  + "RankManager " + SpecialSigns + "->> " + MainColor + "Stworzono range " + ImportantColor + args[0] + SpecialSigns + " (" + WarningColor + "Prefix and Suffix set to 'CHANGE' = DEFAULT value " + SpecialSigns + ")")));
        }else{
            return Util.sendMsg(sender, WarningColor + "Blad: " + WarningColor_2 + "Taka ranga juz istnieje!");
        }
    }
}
