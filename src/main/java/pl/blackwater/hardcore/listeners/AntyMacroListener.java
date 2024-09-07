package pl.blackwater.hardcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.blackwater.hardcore.Core;

public class AntyMacroListener implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR) {
			final Player p = e.getPlayer();
			int count = 1;
			if(Core.getAntyMacroManager().getClickCount().get(p.getUniqueId()) != null) {
				count += Core.getAntyMacroManager().getClickCount().get(p.getUniqueId());
			}
			Core.getAntyMacroManager().getClickCount().put(p.getUniqueId(), count);
		}
	}

}
