package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Util;

public class CombatTask extends BukkitRunnable {
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Combat u = CombatManager.getCombat(p);
            if (u != null) {
                if (u.wasFight() && !u.hasFight()) {
                    Util.sendMsg(p, MessageConfig.MESSAGE_ANTYLOGOUT_ENDCOMBAT);
                    u.setLastAttactkPlayer(null);
                    u.setLastAsystPlayer(null);
                    ActionBarUtil.sendActionBar(p, Util.fixColor(MessageConfig.MESSAGE_ANTYLOGOUT_ACTIONBARENDCOMBAT));
                }
            }
        }
    }
}
