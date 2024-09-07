package pl.blackwater.hardcore.commands;

import org.bukkit.command.CommandSender;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EnableManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class EnableCommand extends Command implements Colors {

    public EnableCommand() {
        super("enable", "Enable command", "/enable [diamondItems] [time]", "core.enable");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length < 2) return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        switch (args[0]){
            case "diamondItems":
                EnableManager enableManager = Core.getEnableManager();
                enableManager.setDiamondItems(Util.parseDateDiff(args[1], true));
                return Util.sendMsg(sender, SpecialSigns + "->> " + MainColor + "Diamentowe itemy beda dostepne za: " + ImportantColor + Util.secondsToString((int)((enableManager.getDiamondItems() - System.currentTimeMillis())/1000)));
        }
        return false;
    }
}
