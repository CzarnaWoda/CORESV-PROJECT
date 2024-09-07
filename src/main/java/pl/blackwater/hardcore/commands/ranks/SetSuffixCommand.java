package pl.blackwater.hardcore.commands.ranks;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class SetSuffixCommand extends Command implements Colors {
    public SetSuffixCommand() {
        super("setsuffix", "Ustawia nowy suffix na danej randze", "/rank setsuffix [rank] [prefix]", "core.rank");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length != 2){
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        Rank r = Core.getRankManager().getRank(args[0]);
        if(r == null){
            return Util.sendMsg(sender, WarningColor + "Blad: " + WarningColor_2 + "Taka ranga nie istnieje, sprawdz ranks.yml!");
        }
        final String suffix = StringUtils.join(args," ", 1, args.length);
        r.setSuffix(suffix);
        r.update(true);
        return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor  + "RankManager " + SpecialSigns + "->> " + MainColor + "Ustawiono nowy suffix " + ImportantColor +  args[1] + MainColor + " dla rangi " + ImportantColor + r.getName())));
    }
}
