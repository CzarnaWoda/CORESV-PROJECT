package pl.blackwater.hardcore.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwaterapi.utils.Util;

public class BlockPlaceListener implements Listener, Colors {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        final int y = e.getBlock().getY();
        final Combat c = CombatManager.getCombat(p);
        if(c != null && c.hasFight() && !p.hasPermission("core.combat.bypass")) {
            if (!CoreConfig.DURINGFIGHT_BLOCKPLACE_ENABLE) {
                if(y <= CoreConfig.DURINGFIGHT_BLOCKPLACE_Y){
                    e.setCancelled(true);
                    Util.sendMsg(p, WarningColor + "Blad: " + WarningColor_2 + "Podczas pvp nie mozna stawiac blokow ponizez Y:" + CoreConfig.DURINGFIGHT_BLOCKPLACE_Y);
                }
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(e.isCancelled()) return;
        if(e.getItemInHand().isSimilar(Core.getCustomItemManager().getSandfarmer())){
            if(e.getBlock().getLocation().subtract(0.0D,1.0D,0.0D).getBlock().getType().equals(Material.ENDER_STONE)){
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            e.getPlayer().getInventory().removeItem(Core.getCustomItemManager().getSandfarmer());
            Location location = e.getBlockPlaced().getLocation();
            while(location.getWorld().getBlockAt(location.subtract(0, 1, 0)).getType() != Material.BEDROCK){
                location.getBlock().setType(Material.SAND);
            }
        }
        if(e.getItemInHand().isSimilar(Core.getCustomItemManager().getBoyfarmer())){
            if(e.getBlock().getLocation().subtract(0.0D,1.0D,0.0D).getBlock().getType().equals(Material.ENDER_STONE)){
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            e.getPlayer().getInventory().removeItem(Core.getCustomItemManager().getBoyfarmer());
            Location location = e.getBlockPlaced().getLocation();
            while(location.getWorld().getBlockAt(location.subtract(0, 1, 0)).getType() != Material.BEDROCK){
                location.getBlock().setType(Material.OBSIDIAN);
            }
        }
        if(e.getItemInHand().isSimilar(Core.getCustomItemManager().getAirgenerator())){
            if(e.getBlock().getLocation().subtract(0.0D,1.0D,0.0D).getBlock().getType().equals(Material.ENDER_STONE)){
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            e.getPlayer().getInventory().removeItem(Core.getCustomItemManager().getAirgenerator());
            Location location = e.getBlockPlaced().getLocation();
            while(location.getWorld().getBlockAt(location.subtract(0, 1, 0)).getType() != Material.BEDROCK){
                location.getBlock().setType(Material.AIR);
            }
        }
        if(e.getItemInHand().isSimilar(Core.getCustomItemManager().getSpecialstone())){
            if(e.getBlock().getLocation().subtract(0.0D,1.0D,0.0D).getBlock().getType().equals(Material.ENDER_STONE)){
                e.setCancelled(true);
                return;
            }
            Util.sendMsg(e.getPlayer(), Util.replaceString(SpecialSigns + "->> " + MainColor + "Polozyles " + ImportantColor + "specjalny kamien" + MainColor + ", mozesz teraz go " + ImportantColor + "zniszczyc!"));
            Core.getSpecialStoneManager().getSpecialStoneLocations().add(e.getBlock().getLocation());
        }
    }
}
