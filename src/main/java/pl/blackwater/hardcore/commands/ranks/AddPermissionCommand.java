package pl.blackwater.hardcore.commands.ranks;

import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class AddPermissionCommand extends Command implements Colors {
    public AddPermissionCommand() {
        super("addpermission", "Dodaje uprawienie do danej rangi", "/rank addpermission [rank] [permission]", "core.rank");
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
        r.addPermission(args[1]);
        r.update(true);
        return Util.sendMsg(sender, Util.fixColor(Util.replaceString(WarningColor  + "RankManager " + SpecialSigns + "->> " + MainColor + "Dodano uprawienie " + ImportantColor +  args[1] + MainColor + " dla rangi " + ImportantColor + r.getName())));
    }
}
