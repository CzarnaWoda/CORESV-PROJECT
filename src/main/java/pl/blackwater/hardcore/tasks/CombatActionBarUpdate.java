package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Util;

public class CombatActionBarUpdate extends BukkitRunnable {

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Combat u = CombatManager.getCombat(p);
            if (u != null && u.hasFight()) {
                long secondsLeft = ((u.getLastAttactTime() / 1000))
                        - (System.currentTimeMillis() / 1000);
                if (secondsLeft >= 0) {
                    ActionBarUtil.sendActionBar(p, Util.fixColor(MessageConfig.MESSAGE_ANTYLOGOUT_INCOMBAT.replace("{SECOND}", String.valueOf(secondsLeft))));
                }
            }
        }
    }
}
