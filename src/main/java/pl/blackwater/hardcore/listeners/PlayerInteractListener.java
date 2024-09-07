package pl.blackwater.hardcore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.isCancelled()) {
            return;
        }
        final Guild g = Core.getGuildManager().getGuild(e.getClickedBlock().getLocation());
        if (g != null && e.getClickedBlock().getType() == Material.DRAGON_EGG) {
            e.setCancelled(true);
            return;
        }
        if (g != null && e.getPlayer().getItemInHand().getType() == Material.BOAT) {
            e.setCancelled(true);
        }
    }
}
