package pl.blackwater.hardcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class BroadcastCommand extends Command implements Colors
{
    public BroadcastCommand() {
        super("broadcast", "ogloszenie do graczy", "/broadcast <wiadomosc>", "core.broadcast", "bc", "bcast");
    }
    
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString("" + SpecialSigns + BOLD + ">> " + ImportantColor + UnderLined + "Ogloszenie " + SpecialSigns + BOLD + "<< " + MainColor + StringUtils.join(args," "))));
        return false;
    }
    //StringUtils.join((Object[])args, " "
}
