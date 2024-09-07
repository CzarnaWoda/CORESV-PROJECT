package pl.blackwater.hardcore.settings;

import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.utils.Util;

import java.util.List;

public class MessageConfig extends ConfigCreator {

    public static String MESSAGE_DEATH_KILL,MESSAGE_DEATH_ASSIST,MESSAGE_COMMAND_UNKNOWNCOMMAND,MESSAGE_ANTYMACRO,MESSAGE_WHITELIST_KICKREASON,MESSAGE_COMMAND_UNKNOWNUSER,MESSAGE_COMMAND_UNKNOWNPLAYER,MESSAGE_COMMAND_GETUSAGE,SERVERNAME_TAG,MESSAGE_ANTYLOGOUT_STARTCOMBAT,MESSAGE_ANTYLOGOUT_INCOMBAT,MESSAGE_ANTYLOGOUT_ENDCOMBAT,MESSAGE_ANTYLOGOUT_ACTIONBARENDCOMBAT;
    public static List<String> MESSAGE_PLAYERJOIN;
    public MessageConfig() {
        super("message.yml", "Message Config", Core.getInstance());

        FileConfiguration config = getConfig();

        MESSAGE_PLAYERJOIN = config.getStringList("message.playerjoin");
        MESSAGE_COMMAND_UNKNOWNCOMMAND = Util.replaceString(config.getString("message.command.unknown.command"));
        MESSAGE_COMMAND_UNKNOWNUSER = Util.replaceString(config.getString("message.command.unknown.user"));
        MESSAGE_COMMAND_UNKNOWNPLAYER = Util.replaceString(config.getString("message.command.unknown.player"));
        MESSAGE_COMMAND_GETUSAGE = Util.fixColor(Util.replaceString(config.getString("message.command.getusage")));
        SERVERNAME_TAG = Util.fixColor(Util.replaceString(config.getString("server.nametag")));
        MESSAGE_ANTYLOGOUT_STARTCOMBAT = Util.fixColor(Util.replaceString(config.getString("message.antylogout.startcombat")));
        MESSAGE_ANTYLOGOUT_INCOMBAT = Util.fixColor(Util.replaceString(config.getString("message.antylogout.incombat")));
        MESSAGE_ANTYLOGOUT_ENDCOMBAT = Util.fixColor(Util.replaceString(config.getString("message.antylogout.endcombat")));
        MESSAGE_ANTYLOGOUT_ACTIONBARENDCOMBAT = Util.fixColor(Util.replaceString(config.getString("message.antylogout.actionbarendcombat")));
        MESSAGE_WHITELIST_KICKREASON = Util.fixColor(Util.replaceString(config.getString("message.whitelist.kickreason")));
        MESSAGE_ANTYMACRO = Util.fixColor(Util.replaceString(config.getString("message.antymacro")));
        MESSAGE_DEATH_KILL = Util.fixColor(Util.replaceString(config.getString("message.death.kill")));
        MESSAGE_DEATH_ASSIST = Util.fixColor(Util.replaceString(config.getString("message.death.assist")));

    }
}
