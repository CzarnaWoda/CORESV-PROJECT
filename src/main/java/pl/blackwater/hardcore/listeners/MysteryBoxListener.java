package pl.blackwater.hardcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EnchantManager;
import pl.blackwater.hardcore.managers.ItemManager;
import pl.blackwater.hardcore.storage.MysteryStorage;
import pl.blackwaterapi.utils.RandomUtil;
import pl.blackwaterapi.utils.Util;

public class MysteryBoxListener implements Listener, Colors {

    @EventHandler
    public void onOpenMysteryBox(BlockPlaceEvent e){
        ItemStack is = Core.getCustomItemManager().getMysterybox();
        if(e.getItemInHand().isSimilar(is)){
            e.setCancelled(true);
            e.getPlayer().getInventory().removeItem(is);
            int random = RandomUtil.getRandInt(0, (MysteryStorage.getMysteryDrops().size()-1));
            ItemStack reward = MysteryStorage.getMysteryDrops().get(random);
            e.getPlayer().getWorld().dropItemNaturally(e.getBlockPlaced().getLocation(), reward);
            String enchants = "";
            if(reward.getEnchantments() != null && reward.getEnchantments().size() != 0){
                enchants = EnchantManager.getEnchantsLevel(reward.getEnchantments());
            }
            String message = MysteryStorage.MYSTERY_DROPMESSAGE;
            message = message.replace("{ITEM}", ItemManager.get(reward.getType())).replace("{AMOUNT}", String.valueOf(reward.getAmount())).replace("{ENCHANTS}", enchants).replace("{NAME}", (reward.getItemMeta().getDisplayName() != null ? " &7o nazwie: " + reward.getItemMeta().getDisplayName() : "")).replace("{NL}", "\n");
            message = Util.replaceString(message);
            Util.sendMsg(e.getPlayer(), message);
        }
    }
}
