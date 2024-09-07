package pl.blackwater.hardcore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;

public class PlayerClassListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 1){
            final User user = Core.getUserManager().getUser(e.getPlayer());
            if(user.getPlayerClassType().equals(PlayerClassType.WARRIOR)){
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,6*20,1));
            }
        }
    }
}
