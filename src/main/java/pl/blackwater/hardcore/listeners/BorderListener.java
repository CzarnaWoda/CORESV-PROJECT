package pl.blackwater.hardcore.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwaterapi.utils.Util;

public class BorderListener implements Listener, Colors {

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.getWorld().getName().toLowerCase().equals("world") && (e.getTo().getX() > CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS || e.getTo().getX() < -CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS || e.getTo().getZ() > CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS || e.getTo().getZ() < -CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS)) {
            if (e.getFrom().getBlockX() + 1 == e.getTo().getBlockX()) {
                e.getPlayer().setVelocity(new Vector(-1, 0, 0));
            }
            if (e.getFrom().getBlockZ() + 1 == e.getTo().getBlockZ()) {
                e.getPlayer().setVelocity(new Vector(0, 0, -1));
            }
            p.teleport(e.getFrom());
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.75f);
            p.sendMessage(Util.fixColor("&8• &eOsiagnales granice swiata! &7(&e" + CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS + " kratek&7)"));
        }
    }

    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        if (p.getWorld().getName().toLowerCase().equals("world") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS-10 || e.getTo().getX() < -CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS-10 || e.getTo().getZ() > CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS-10 || e.getTo().getZ() < -CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS-10)) {
            e.setCancelled(true);
            p.sendMessage(Util.fixColor("&8• &eOsiagnales granice swiata! &7(&e" + CoreConfig.BORDERMANAGER_BORDER_WORLDRADIUS + " kratek&7)"));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.75f);
        }
    }

    @EventHandler
    public void onMove1(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.getWorld().getName().toLowerCase().equals("world_nether") && (e.getTo().getX() > CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS || e.getTo().getX() < -CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS || e.getTo().getZ() > CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS || e.getTo().getZ() < -CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS)) {
            if (e.getFrom().getBlockX() + 1 == e.getTo().getBlockX()) {
                e.getPlayer().setVelocity(new Vector(-1, 0, 0));
            }
            if (e.getFrom().getBlockZ() + 1 == e.getTo().getBlockZ()) {
                e.getPlayer().setVelocity(new Vector(0, 0, -1));
            }
            p.teleport(e.getFrom());
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.75f);
            p.sendMessage(Util.fixColor("&8• &eOsiagnales granice swiata! &7(&e" + CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS + " kratek&7)"));
        }
    }

    @EventHandler
    public void onPlayerTeleport1(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        if (p.getWorld().getName().toLowerCase().equals("world_nether") && e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && (e.getTo().getX() > CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS-10 || e.getTo().getX() < -CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS-10 || e.getTo().getZ() > CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS-10 || e.getTo().getZ() < -CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS-10)) {
            e.setCancelled(true);
            p.sendMessage(Util.fixColor("&8• &eOsiagnales granice swiata! &7(&e" + CoreConfig.BORDERMANAGER_BORDER_NETHERRADIUS + " kratek&7)"));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.75f);
        }
    }

}
