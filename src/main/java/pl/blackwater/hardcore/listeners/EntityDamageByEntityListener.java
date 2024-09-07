package pl.blackwater.hardcore.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player d = Util.getDamager(e);
        if (d == null) {
            return;
        }
        final Player p = (Player) e.getEntity();
        if (p.equals(d)) {
            return;
        }
        final Combat u = CombatManager.getCombat(p);
        if (u == null) {
            return;
        }
        if (!u.hasFight()) {
            Util.sendMsg(p, MessageConfig.MESSAGE_ANTYLOGOUT_STARTCOMBAT.replace("{SECOND}", String.valueOf(CoreConfig.COMBATMANAGER_ESCAPETIME)));
        }
        u.setLastAttactTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(CoreConfig.COMBATMANAGER_ESCAPETIME));
        if (u.getLastAttactkPlayer() != d) {
            u.setLastAsystPlayer(u.getLastAttactkPlayer());
            u.setLastAsystTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(CoreConfig.COMBATMANAGER_ESCAPETIME));
        }
        u.setLastAttactkPlayer(d);
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent e) {
        if ((e.getDamager() instanceof Arrow) && e.getEntity() instanceof Player && Util.getDamager(e) != null) {
            Player p = (Player) e.getEntity();
            Player o = Util.getDamager(e);
            ItemStack item = p.getItemInHand();
            if (p.equals(o) || o.equals(p)) {
                final User user = Core.getUserManager().getUser(p);
                if(!user.getPlayerClassType().equals(PlayerClassType.ARCHER)) {
                    e.setCancelled(true);
                    Util.sendMsg(p, "&4Blad: &cNie mozna siebie punchowac !");
                }
            }
        }
    }
}