package pl.blackwater.hardcore.tasks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwaterapi.utils.ItemStackUtil;
import pl.blackwaterapi.utils.Util;

public class DepositTask extends BukkitRunnable implements Colors {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            final int lk = ItemStackUtil.getAmountOfItem(Material.GOLDEN_APPLE, p,(short)1);
            final int lr = ItemStackUtil.getAmountOfItem(Material.GOLDEN_APPLE, p, (short)0);
            final int lp = ItemStackUtil.getAmountOfItem(Material.ENDER_PEARL, p,(short)0);
            if(lk > CoreConfig.DEPOSITMANAGER_LIMITKOX){
                final User u = Core.getUserManager().getUser(p);
                final ItemStack goldenapple_1 = new ItemStack(Material.GOLDEN_APPLE, lk - CoreConfig.DEPOSITMANAGER_LIMITKOX,(short)1);
                ItemStackUtil.remove(goldenapple_1, p,CoreConfig.DEPOSITMANAGER_LIMITKOX);
                u.addDepositKox(lk - CoreConfig.DEPOSITMANAGER_LIMITKOX);
                final int i2 = lk - CoreConfig.DEPOSITMANAGER_LIMITKOX;
                Util.sendMsg(p, Util.replaceString(SpecialSigns + "* " + MainColor + "Posiadasz przy sobie zbyt duza ilosc " + ChatColor.GOLD + "koxow! " + ImportantColor + i2 + MainColor + " zostaly przeniesione do schowka " + SpecialSigns + "(" + WarningColor + "/schowek" + SpecialSigns + ")"));
            }
            if(lr > CoreConfig.DEPOSITMANAGER_LIMITREF){
                final User u = Core.getUserManager().getUser(p);
                final ItemStack goldenapple = new ItemStack(Material.GOLDEN_APPLE, lr - CoreConfig.DEPOSITMANAGER_LIMITREF,(short)0);
                ItemStackUtil.remove(goldenapple, p,CoreConfig.DEPOSITMANAGER_LIMITREF);
                u.addDepositRef(lr - CoreConfig.DEPOSITMANAGER_LIMITREF);
                final int i2 = lr - CoreConfig.DEPOSITMANAGER_LIMITREF;
                Util.sendMsg(p, Util.replaceString(SpecialSigns + "* " + MainColor + "Posiadasz przy sobie zbyt duza ilosc " + ChatColor.YELLOW + "refili! " + ImportantColor + i2 + MainColor + " zostaly przeniesione do schowka " + SpecialSigns + "(" + WarningColor + "/schowek" + SpecialSigns + ")"));
            }
            if(lp > CoreConfig.DEPOSITMANAGER_LIMITPEARL){
                final User u = Core.getUserManager().getUser(p);
                final ItemStack pearl = new ItemStack(Material.ENDER_PEARL, lp - CoreConfig.DEPOSITMANAGER_LIMITPEARL,(short)0);
                ItemStackUtil.remove(pearl, p,CoreConfig.DEPOSITMANAGER_LIMITPEARL);
                u.addDepositPearl(lp - CoreConfig.DEPOSITMANAGER_LIMITPEARL);
                final int i2 = lp - CoreConfig.DEPOSITMANAGER_LIMITPEARL;
                Util.sendMsg(p, Util.replaceString(SpecialSigns + "* " + MainColor + "Posiadasz przy sobie zbyt duza ilosc " + ChatColor.LIGHT_PURPLE + "perel! " + ImportantColor + i2 + MainColor + " zostaly przeniesione do schowka " + SpecialSigns + "(" + WarningColor + "/schowek" + SpecialSigns + ")"));
            }
        }
    }
}
