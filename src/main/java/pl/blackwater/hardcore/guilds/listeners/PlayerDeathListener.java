package pl.blackwater.hardcore.guilds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.utils.DeathUtil;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

public class PlayerDeathListener implements Listener, Colors {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.setDeathMessage(null);
        final Player p = e.getEntity();
        final Combat pC = CombatManager.getCombat(p);
        final User u = Core.getUserManager().getUser(p);
        respawn(p);
        Player k = p.getKiller();
        if(k == null && pC != null && pC.wasFight()){
            k = pC.getLastAttactkPlayer();
        }
        else if(k != null){
            final User kUser = Core.getUserManager().getUser(k);
            if(kUser == null){
                return;
            }
            if(p.equals(k)){
                return;
            }
            if(kUser.getPlayerClassType().equals(PlayerClassType.WARRIOR)){
                k.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,15*20,0));
            }
            if(DeathUtil.isLastKill(kUser,p)){
                Util.sendMsg(k, WarningColor + "Uwaga, zabiles tego samego gracza w ciagu 4h" + WarningColor_2 + " nie otrzymujesz zadnych punktow!");
                Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(DeathUtil.deathMessage(0,0,p,k))));
                if(DeathUtil.isAsyst(pC)){
                    assist(pC,u);
                }
            }else{
                int add = (int)(64.0 + (kUser.getPoints() - (u != null ? u.getPoints() : 0)) * -0.25);
                if(add <= 0){
                    add = 0;
                }
                final int remove = add / 4 * 3;
                if(u != null){
                    u.removePoints(remove);
                }
                kUser.setLastKillTime(System.currentTimeMillis() + TimeUtil.HOUR.getTime(4));
                kUser.setLastKill(p.getDisplayName());
                kUser.addPoints(add);
                kUser.addKills(1);
                kUser.update(false);
                Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(DeathUtil.deathMessage(add,remove,p,k))));
                if(DeathUtil.isAsyst(pC)){
                    if (u != null) {
                        assist(pC,u);
                    }
                }
            }
        }else if (u != null){
            u.removePoints(5);
        }
        if(u != null) {
            u.addDeaths(1);
            u.update(false);
        }
        DeathUtil.remove(pC);
        p.getLocation().getWorld().dropItemNaturally(p.getLocation(), ItemUtil.getPlayerHead(p.getDisplayName()));
    }
    private void assist(final Combat pC, final User pU){
        final User aU = Core.getUserManager().getUser(pC.getLastAsystPlayer());
        int assist = (int)((48.0 + (aU != null ? aU.getPoints() : 0) - pU.getPoints() * -0.25)/3.0);
        if(assist <= 0){
            assist = 0;
        }
        if(aU != null){
            aU.addAssist(1);
            aU.addPoints(assist);
            aU.update(false);
        }
        Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(DeathUtil.assistMessage(assist,pC.getLastAsystPlayer()))));
    }
    private void respawn(Player p){
        Bukkit.getScheduler().runTask(Core.getInstance(), () -> DeathUtil.strike(p.getLocation()));
        new BukkitRunnable(){
            public void run(){
                if(p.isInsideVehicle()){
                    p.leaveVehicle();
                }
                p.spigot().respawn();
            }
        }.runTaskLater(Core.getInstance(), 5L);
    }
}
