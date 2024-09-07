package pl.blackwater.hardcore.drop.listeners;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.managers.DropManager;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.ItemManager;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.RandomUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;


public class DropBlockBreakListener implements Listener, Colors
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        final Block b = e.getBlock();
        if (!e.isCancelled() && p.getGameMode().equals(GameMode.SURVIVAL)) {
            if(e.getBlock().getType().equals(Core.getCustomItemManager().getSpecialstone().getType())){
                if(Core.getSpecialStoneManager().getSpecialStoneLocations().contains(e.getBlock().getLocation())){
                    Core.getSpecialStoneManager().getSpecialStoneLocations().remove(e.getBlock().getLocation());
                    e.getBlock().setType(Material.AIR);
                    final ItemStack reward = Core.getSpecialStoneManager().getSpecialStoneItems().get(RandomUtil.getRandInt(0,Core.getSpecialStoneManager().getSpecialStoneItems().size() - 1));
                    ItemUtil.giveItems(Collections.singletonList(reward),e.getPlayer());
                    p.spigot().playEffect(b.getLocation(), Effect.COLOURED_DUST, 1, 5, 0.2f, 0.3f, 0.2f, 0.1f, 10, 1000);
                    Util.sendMsg(e.getPlayer(),Util.replaceString(SpecialSigns + "->> " + MainColor + "Trafiles na: " + ImportantColor + ItemManager.get(reward.getType()) + MainColor + "x" + ImportantColor + reward.getAmount()));
                    e.setCancelled(true);
                    return;
                }
            }
            final User u = Core.getUserManager().getUser(p);
            if(e.getBlock().getType().equals(Material.STONE)){
                if(u.getPlayerClassType().equals(PlayerClassType.DIGGER)){
                    if(RandomUtil.getChance(0.33)){
                        if(p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                            p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        }
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 90 * 20, 1));
                            Util.sendMsg(p, Util.fixColor(Util.replaceString(ImportantColor + "GORNIK " + SpecialSigns + "->> " + MainColor + "Otrzymales " + ImportantColor + "HASTE II na 90s")));
                        }
                    if(!u.isTurboDrop()) {
                        if(RandomUtil.getChance(0.15)){
                            final long eventtime = Util.parseDateDiff("10m", true);
                            u.setTurboDrop(true);
                            u.setTurboDropTime(eventtime);
                            Util.sendMsg(p,Util.fixColor(Util.replaceString(ImportantColor + "GORNIK " + SpecialSigns + "->> " + MainColor + "Otrzymales " + ImportantColor + "TURBODROP na 10m")));
                        }
                    }
                }
            }
            p.giveExp(DropManager.getExp(b.getType()));
            DropManager.getDropData(b.getType()).breakBlock(b, p, p.getItemInHand());
            p.spigot().playEffect(b.getLocation(), Effect.FLAME, 1, 5, 0.2f, 0.3f, 0.2f, 0.1f, 10, 1000);
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        final Block block = e.getBlock();
        if (block.getType().equals(Material.STONE)) {
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.5f, (float)(Math.random() * 20.0) / 10.0f);
        }
        else if (block.getType().equals(Material.OBSIDIAN)) {
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.5f, (float)(Math.random() * 20.0) / 10.0f);
        }
    }
}
