package pl.blackwater.hardcore.commands;


import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.TitleUtil;
import pl.blackwaterapi.utils.Util;

public class BroadCastTitleCommand extends PlayerCommand{

	public BroadCastTitleCommand() {
		super("titlebc", "broadcast", "/titlebc [naglowek] [tekst]", "core.titlebc");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(Player p, String[] args) {
        if (args.length < 2) {
            return Util.sendMsg(p, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        for (Player p1 : Bukkit.getOnlinePlayers()){
        TitleUtil.sendFullTitle(p1, 20, 100, 30, Util.fixColor(args[0].replaceAll("_", " ")), StringUtils.join(args," ",1,args.length));
        }
        return false;
	}

}
