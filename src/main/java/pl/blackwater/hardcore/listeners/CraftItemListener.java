package pl.blackwater.hardcore.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.utils.ItemUtil;

import java.util.Collections;

public class CraftItemListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent e){
        final CraftingInventory inventory = e.getInventory();
        if(inventory.getType().equals(InventoryType.WORKBENCH) && e.getSlotType().toString().equalsIgnoreCase("RESULT") && e.getCurrentItem().getType().equals(Core.getCustomItemManager().getSpecialstone().getType())){
            for(int i = 1; i < 10; i++){
                if(inventory.getItem(i).getAmount() != 64){
                    e.setCancelled(true);
                    return;
                }
            }
            for(int i = 1; i < 10; i ++){
                inventory.setItem(i, new ItemStack(Material.AIR,0));
            }
            inventory.setResult(new ItemStack(Material.AIR));
            ItemUtil.giveItems(Collections.singletonList(Core.getCustomItemManager().getSpecialstone()), (Player) e.getWhoClicked());
        }
    }
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e){
        if(e.getInventory().getResult().equals(Core.getCustomItemManager().getSpecialstone())){
            for(int i = 1; i < 10; i ++){
                if(e.getInventory().getItem(i).getAmount() != 64){
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }
}
