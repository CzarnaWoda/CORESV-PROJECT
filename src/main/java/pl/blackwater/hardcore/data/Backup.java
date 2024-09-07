package pl.blackwater.hardcore.data;

import lombok.Data;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.enums.BackupType;
import pl.blackwater.hardcore.utils.Base64Util;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
public class Backup implements Entry {

    private UUID backupUUID;
    private UUID owner;
    private ItemStack[] armor,inventory;
    private long backupTime;
    private long takeBackupTime;
    private BackupType backupType;
    private String backupCreator;

    public Backup(UUID backupUUID, UUID owner, ItemStack[] armor, ItemStack[] inventory, long backupTime, long takeBackupTime, BackupType backupType, String backupCreator){
        this.backupUUID = backupUUID;
        this.owner = owner;
        this.armor = armor;
        this.inventory = inventory;
        this.backupTime = backupTime;
        this.takeBackupTime =  takeBackupTime;
        this.backupType = backupType;
        this.backupCreator = backupCreator;
        insert();
    }
    public Backup(ResultSet rs) throws SQLException,IOException{
        this.backupUUID = UUID.fromString(rs.getString("backupUUID"));
        this.owner = UUID.fromString(rs.getString("owner"));
        this.armor = Base64Util.itemStackArrayFromBase64(rs.getString("armor"));
        this.inventory = Base64Util.itemStackArrayFromBase64(rs.getString("inventory"));
        this.backupTime = rs.getLong("backupTime");
        this.takeBackupTime = rs.getLong("takeBackupTime");
        this.backupType = BackupType.getByName(rs.getString("backupType"));
        this.backupCreator = rs.getString("backupCreator");
    }
    @Override
    public void insert() {
        String sql = "INSERT INTO `{P}backups` (`id`,`backupUUID`,`owner`,`armor`,`inventory`,`backupTime`,`takeBackupTime`,`backupType`,`backupCreator`) VALUES (NULL, '" + getBackupUUID().toString() + "', '" + getOwner().toString() + "', '" + Base64Util.itemStackArrayToBase64(getArmor()) + "', '" + Base64Util.itemStackArrayToBase64(getInventory()) + "', '" + getBackupTime() + "', '" + getTakeBackupTime() + "', '" + getBackupType().getName() + "', '" + getBackupCreator() + "')";
        API.getStore().update(sql);
    }

    @Override
    public void update(boolean b) {
        String sql = "UPDATE `{P}backups` SET `takeBackupTime`='" + getTakeBackupTime() + "' WHERE `backupUUID`='" + getBackupUUID() + "'";
        API.getStore().update(b, sql);
    }

    @Override
    public void delete()  {
        API.getStore().update(false, "DELETE FROM `{P}backups` WHERE `backupUUID` = '" + getBackupUUID() + "'");
    }
}
