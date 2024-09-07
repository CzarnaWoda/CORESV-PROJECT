package pl.blackwater.hardcore.guilds.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.utils.RandomUtil;
import pl.blackwaterapi.utils.SpaceUtil;
import pl.blackwaterapi.utils.TimeUtil;

import java.util.Calendar;
import java.util.List;

public class GuildExplodeListener implements Listener, Colors {

    @EventHandler(priority = EventPriority.MONITOR)
    public void explode(final EntityExplodeEvent e) {
        Guild g = Core.getGuildManager().getGuild(e.getEntity().getLocation());
        if (g == null) {
            for (final Block b : e.blockList()) {
                if (g != null) {
                    continue;
                }
                final Guild o = Core.getGuildManager().getGuild(b.getLocation());
                if (o == null) {
                    continue;
                }
                g = o;
            }
        }
        if (g == null) {
            e.setCancelled(true);
            return;
        }
        g.setLastExplodeTime(System.currentTimeMillis() + TimeUtil.MINUTE.getTime(2));
        g.sendMessage(guildPrefix + WarningColor + "Na terenie twojej gildii wybuchlo tnt");
        if (g.getCuboid().isInCentrum(e.getLocation(), 3, 1, 2)) {
            e.setCancelled(true);
            return;
        }
        if (g.getCreateTime() + TimeUtil.HOUR.getTime(24) > System.currentTimeMillis()) {
            e.setCancelled(true);
            return;
        }
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY) > 22 || c.get(Calendar.HOUR_OF_DAY) < 10) {
            e.setCancelled(true);
            return;
        }
        final List<Location> sphere = SpaceUtil.sphere(e.getLocation(), 5, 5, false, true, 0);
        for (final Location location : sphere) {
            if (location.getBlock().getType() == Material.OBSIDIAN) {
                if (!RandomUtil.getChance(5.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else if (location.getBlock().getType() == Material.STATIONARY_WATER) {
                if (!RandomUtil.getChance(10.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else if (location.getBlock().getType() == Material.STATIONARY_LAVA) {
                if (!RandomUtil.getChance(10.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else if (location.getBlock().getType() == Material.WATER) {
                if (!RandomUtil.getChance(50.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else if (location.getBlock().getType() == Material.LAVA) {
                if (!RandomUtil.getChance(50.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else if (location.getBlock().getType() == Material.ENDER_CHEST) {
                if (!RandomUtil.getChance(50.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
            else {
                if (location.getBlock().getType() != Material.ENCHANTMENT_TABLE || !RandomUtil.getChance(50.0)) {
                    continue;
                }
                location.getBlock().setType(Material.AIR);
            }
        }
    }
}
