package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.data.Backup;
import pl.blackwater.hardcore.enums.BackupType;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class BackupManager {
    @Getter private HashMap<UUID, Backup> backups = new HashMap<>();

    public Backup createBackup(Player player, BackupType backupType, String creator){
        Backup backup = new Backup(UUID.randomUUID(), player.getUniqueId(), player.getInventory().getArmorContents(), player.getInventory().getContents(), System.currentTimeMillis(), 0L, backupType,creator);
        backups.put(backup.getBackupUUID(), backup);
        List<Backup> backupList = getBackups(backup.getOwner(), backup.getBackupType());
        List<Long> list = new ArrayList<>();
        if(backupList.size() > 5){
            for(Backup backup1 : backupList){
                list.add(backup1.getBackupTime());
            }
            long min = list.stream().min(Comparator.comparing(Long::intValue)).get();
            for(Backup remove : backupList){
                if(remove.getBackupTime() == min){
                    deleteBackup(remove);
                }
            }
        }
        return backup;
    }
    public Backup getBackup(UUID BackupUUID){
        return backups.get(BackupUUID);
    }
    public List<Backup> getBackups(UUID playerUUID, BackupType backupType) {
        List<Backup> backupList = new ArrayList<>();
        for (Backup b : backups.values())
            if (b.getOwner().equals(playerUUID) && b.getBackupType().equals(backupType)) backupList.add(b);
        return backupList;
    }
    public void deleteBackup(Backup b){
        backups.remove(b.getBackupUUID());
        b.delete();
    }
    public void load(){
        final ResultSet rs = API.getStore().query("SELECT * FROM `{P}backups`");
        try {
            while (rs.next()){
                Backup b = new Backup(rs);
                backups.put(b.getBackupUUID(), b);
            }
            CoreUtil.sendConsoleMessage("Loaded " + backups.size() + " backups!");
        }catch (SQLException | IOException sql){
            Logger.warning("ERROR WITH LOAD BACKUPS - SQLEXCEPTION CHECK ERROR: ");
            sql.printStackTrace();
        }
    }
}
