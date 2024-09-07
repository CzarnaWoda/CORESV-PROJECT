package pl.blackwater.hardcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.CombatManager;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCloseOpenInventoryListener implements Listener, Colors {
    public static HashMap<UUID,Long> times = new HashMap<>();
    public static HashMap<UUID, Integer> counts = new HashMap<>();
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        if(e.getInventory() != null && e.getInventory().getType().equals(InventoryType.ENDER_CHEST)){
            e.setCancelled(true);
            final Player player = (Player) e.getPlayer();
            final Combat combat = CombatManager.getCombat(player);
            if(combat != null && combat.hasFight()){
                Util.sendMsg(player,Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor + "Nie mozna otwierac enderchest'a podczas walki!")));
                return;
            }
            if(times.get(player.getUniqueId()) != null && times.get(player.getUniqueId()) >= System.currentTimeMillis()){
                Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor + "Musisz poczekac jeszcze chwile zanim kolejny raz otworzysz enderchest")));
            }else {
                final Inventory inventory = Bukkit.createInventory(player, (player.hasPermission("core.enderchest.vip") ? 5*9 : 3*9), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.LIGHT_PURPLE + UnderLined + "ENDERCHEST" + SpecialSigns + " <<-")));
                inventory.setContents(Core.getUserManager().getUser(player).getEnderChest());
                player.openInventory(inventory);
                times.put(player.getUniqueId(), System.currentTimeMillis() + 10000L);
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.LIGHT_PURPLE + UnderLined + "ENDERCHEST" + SpecialSigns + " <<-")))){
            final Player player = (Player) e.getPlayer();
            Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Rozpoczynam zapis " + ChatColor.LIGHT_PURPLE + "enderchest'u" + MainColor + "...")));
            final long start = System.currentTimeMillis();
            final User user = Core.getUserManager().getUser(player);
            user.setEnderChest(e.getInventory().getContents());
            user.saveEnderChest();
            Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zapis " + ChatColor.LIGHT_PURPLE + "enderchest'u" + MainColor + " trwal " + ImportantColor + (System.currentTimeMillis()-start) + MainColor + "ms")));
        }
        if(e.getInventory() != null && e.getInventory().getName().equalsIgnoreCase(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wybierz swoja " + ImportantColor + "klase")))){
            final Player player = (Player) e.getPlayer();
            final User user = Core.getUserManager().getUser(player);
            if(user.getPlayerClassType() == PlayerClassType.UNKNOWN){
                if(counts.get(player.getUniqueId()) == null){
                    counts.put(player.getUniqueId(), 0);
                }else{
                    counts.replace(player.getUniqueId(), counts.get(player.getUniqueId())+1);
                    if(counts.get(player.getUniqueId()) >= 2){
                        player.kickPlayer(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Musisz wybrac klase!")));
                        counts.remove(player.getUniqueId());
                        return;
                    }
                }
                Bukkit.getScheduler().runTaskLater(Core.getInstance(),() -> {
                    if(user.getPlayerClassType().equals(PlayerClassType.UNKNOWN)){
                        Core.getPlayerClassInventory().openPlayerClassInventory(player);
                    }
                }, 20L);
            }
        }
    }
}
