package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.enums.BackupType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.utils.Util;

public class BackupTask extends BukkitRunnable implements Colors {
    @Override
    public void run() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("core.admin")) {
                Util.sendMsg(online, Util.fixColor(Util.replaceString(ImportantColor + "BACKUPMANAGER " + SpecialSigns + "->> " + WarningColor + "Trwa tworzenie automatycznego zapisu ekwipunku na serwerze!")));
            }
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            Util.sendMsg(online, Util.replaceString(SpecialSigns + "->> " + MainColor + "Trwa " + ImportantColor + "zapisywanie twojego ekwipunku..."));
            Core.getBackupManager().createBackup(online, BackupType.AUTOMATIC, "CONSOLE");
            Util.sendMsg(online, Util.replaceString(SpecialSigns + "->> " + MainColor + "Zapis twojego ekwipunku zostal pomyslnie " + ImportantColor + "ukonczony"));
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("core.admin")) {
                Util.sendMsg(online,Util.fixColor(Util.replaceString(ImportantColor + "BACKUPMANAGER " + SpecialSigns + "->> " + WarningColor + "Zakonczono automatyczny zapis ekwipunku na serwerze!")));
            }
        }
    }
}
