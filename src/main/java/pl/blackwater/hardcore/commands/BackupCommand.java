package pl.blackwater.hardcore.commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.enums.BackupType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.inventories.BackupInventory;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class BackupCommand extends PlayerCommand implements Colors {
    public BackupCommand() {
        super("backup", "backup command", "/backup [menu/create] [player]", "core.backup");
    }
    @Getter
    private static List<Player> findPlayer = new ArrayList<>();
    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length < 1){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        if (!args[0].equalsIgnoreCase("menu")) {
            if (args[0].equalsIgnoreCase("create") && (args.length >= 2)){
                Player o = Bukkit.getPlayer(args[1]);
                if(o != null){
                    Util.sendMsg(player, Util.replaceString(ImportantColor + "&nBACKUPMANAGER" + SpecialSigns + " ->> " + MainColor + "Trwa tworzenie zapisu ekwipunku gracza " + ImportantColor + o.getName()));
                    Core.getBackupManager().createBackup(o, BackupType.MANUAL, player.getName());
                    return Util.sendMsg(player, Util.replaceString(ImportantColor + "&nBACKUPMANAGER" + SpecialSigns + " ->> " + MainColor + "Stworzono zapis ekwipunku gracza " + ImportantColor + o.getName()));
                }else{
                    return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
                }
            }else{
                return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
            }
        } else {
            BackupInventory.openInventory().openInventory(player);
            return true;
        }
    }
}
