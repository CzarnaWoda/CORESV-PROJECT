package pl.blackwater.hardcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.blackwater.hardcore.commands.VanishCommand;

public class VanishListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		for(Player vanished : VanishCommand.getVanished()) {
			final Player p = e.getPlayer();
			if(!p.hasPermission("core.vanish"))
				p.hidePlayer(vanished);
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		VanishCommand.getVanished().remove(e.getPlayer());
	}
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e){
		if(VanishCommand.getVanished().contains(e.getPlayer()))
			e.setCancelled(true);
	}
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e){
		if(VanishCommand.getVanished().contains(e.getPlayer()))
			e.setCancelled(true);
	}
}
