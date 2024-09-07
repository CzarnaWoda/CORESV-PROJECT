package pl.blackwater.hardcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;

public class GodListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			User u = Core.getUserManager().getUser(p);
			if(u.isGod())
				e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			User u = Core.getUserManager().getUser(p);
			if(u.isGod())
				e.setCancelled(true);
		}
	}

}
