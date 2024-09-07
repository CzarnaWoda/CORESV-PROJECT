package pl.blackwater.hardcore.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EnableManager;
import pl.blackwaterapi.utils.Util;

public class DiamondItemsListener implements Listener, Colors {

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if(e.getRecipe().getResult().getType().equals(Material.DIAMOND_HELMET) || e.getRecipe().getResult().getType().equals(Material.DIAMOND_CHESTPLATE) || e.getRecipe().getResult().getType().equals(Material.DIAMOND_LEGGINGS) || e.getRecipe().getResult().getType().equals(Material.DIAMOND_BOOTS) || e.getRecipe().getResult().getType().equals(Material.DIAMOND_SWORD)){
            EnableManager enableManager = Core.getEnableManager();
            if(!enableManager.isDiamondItems()){
                e.setCancelled(true);
                Util.sendMsg(e.getWhoClicked(), Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Diamentowe itemy beda dostepne dopiero za: " + WarningColor + Util.secondsToString((int) ((enableManager.getDiamondItems()-System.currentTimeMillis())/1000))));
            }
        }
    }

    @EventHandler
    public void onWear(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getType().equals(InventoryType.PLAYER)){
            if(e.getWhoClicked() instanceof Player){
                Player p = (Player) e.getWhoClicked();
                EnableManager enableManager = Core.getEnableManager();
                if(!enableManager.isDiamondItems()){
                    if(e.getCursor().getType().equals(Material.DIAMOND_HELMET) || e.getCursor().getType().equals(Material.DIAMOND_CHESTPLATE) || e.getCursor().getType().equals(Material.DIAMOND_LEGGINGS) || e.getCursor().getType().equals(Material.DIAMOND_BOOTS) || e.getCursor().getType().equals(Material.DIAMOND_SWORD)){
                        e.setCancelled(true);
                        Util.sendMsg(e.getWhoClicked(), Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Diamentowe itemy beda dostepne dopiero za: " + WarningColor + Util.secondsToString((int) ((enableManager.getDiamondItems()-System.currentTimeMillis())/1000))));
                    }
                }
            }
        }
    }
}
