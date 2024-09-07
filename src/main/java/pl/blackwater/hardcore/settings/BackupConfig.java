package pl.blackwater.hardcore.settings;

import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.configs.ConfigCreator;

public class BackupConfig extends ConfigCreator {

    public static int BACKUP_AUTOMATIC_DELAY;
    public BackupConfig() {
        super("backup.yml", "backup config", Core.getInstance());
        BACKUP_AUTOMATIC_DELAY = getConfig().getInt("backup.automatic.delay");
    }
}
