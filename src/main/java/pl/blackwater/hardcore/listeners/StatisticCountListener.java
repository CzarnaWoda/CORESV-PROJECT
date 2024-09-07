package pl.blackwater.hardcore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.blackwater.hardcore.Core;

public class StatisticCountListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 0){
            Core.getUserManager().getUser(e.getPlayer()).addEatedRef(1);
        }
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 1){
            Core.getUserManager().getUser(e.getPlayer()).addEatedKox(1);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(e.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
        Core.getUserManager().getUser(e.getPlayer()).addThrowedPearl(1);
    }
}
