package pl.blackwater.hardcore.inventories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.utils.MaterialUtil;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemStackUtil;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepositInventory implements Colors {

    public static void openDepositMenu(Player player){
        InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.LIGHT_PURPLE + UnderLined + "DEPOZYT" + SpecialSigns + " <<-")),3);
        final ItemBuilder magneta = MaterialUtil.getStainedGlassPane((short)2);
        final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short)3);
        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short)11);
        final ItemBuilder pink = MaterialUtil.getStainedGlassPane((short)6);

        inventoryGUI.setItem(0,magneta.build(),null);
        inventoryGUI.setItem(1,lightblue.build(),null);
        inventoryGUI.setItem(2,magneta.build(),null);
        inventoryGUI.setItem(3,blue.build(),null);
        inventoryGUI.setItem(4,blue.build(),null);
        inventoryGUI.setItem(5,blue.build(),null);
        inventoryGUI.setItem(6,magneta.build(),null);
        inventoryGUI.setItem(7,lightblue.build(),null);
        inventoryGUI.setItem(8,magneta.build(),null);
        inventoryGUI.setItem(9,lightblue.build(),null);
        inventoryGUI.setItem(10,pink.build(),null);
        final User user = Core.getUserManager().getUser(player);
        final ItemBuilder kox = new ItemBuilder(Material.GOLDEN_APPLE,1,(short)1).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.GOLD + UnderLined + "KOXY" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny limit na serwerze: " + ChatColor.GOLD + CoreConfig.DEPOSITMANAGER_LIMITKOX)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.GOLD + UnderLined + user.getDepositKox())));
        inventoryGUI.setItem(11,kox.build(), (player1, inventory, i, itemStack) -> {
            if(user.getDepositKox() > 0){
                user.removeDepositKox(1);
                final ItemStack a = new ItemStack(Material.GOLDEN_APPLE,1,(short)1);
                ItemUtil.giveItems(Collections.singletonList(a),player);
                final ItemMeta meta = itemStack.getItemMeta();
                final List<String> lore = meta.getLore();
                lore.set(1,Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.GOLD + UnderLined + user.getDepositKox())));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2  + "Nie mozesz tego zrobic jezeli nie masz przynajmniej jednego przedmiotu tego rodzaju w schowku");
            }
        });
        inventoryGUI.setItem(12,pink.build(),null);
        final ItemBuilder pearl = new ItemBuilder(Material.ENDER_PEARL).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.LIGHT_PURPLE + UnderLined + "PERLY" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny limit na serwerze: " + ChatColor.LIGHT_PURPLE + CoreConfig.DEPOSITMANAGER_LIMITPEARL)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.LIGHT_PURPLE + UnderLined + user.getDepositPearl())));
        inventoryGUI.setItem(13,pearl.build(), (player1, inventory, i, itemStack) -> {
            if(user.getDepositPearl() > 0){
                user.removeDepositPearl(1);
                final ItemStack a = new ItemStack(Material.ENDER_PEARL,1);
                ItemUtil.giveItems(Collections.singletonList(a),player);
                final ItemMeta meta = itemStack.getItemMeta();
                final List<String> lore = meta.getLore();
                lore.set(1,Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.LIGHT_PURPLE + UnderLined + user.getDepositPearl())));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2  + "Nie mozesz tego zrobic jezeli nie masz przynajmniej jednego przedmiotu tego rodzaju w schowku");
            }
        });
        inventoryGUI.setItem(14,pink.build(),null);
        final ItemBuilder ref = new ItemBuilder(Material.GOLDEN_APPLE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.YELLOW + UnderLined + "REFILE" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualny limit na serwerze: " + ChatColor.YELLOW + CoreConfig.DEPOSITMANAGER_LIMITREF)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.YELLOW + UnderLined + user.getDepositRef())));
        inventoryGUI.setItem(15,ref.build(), (player1, inventory, i, itemStack) -> {
            if(user.getDepositRef() > 0){
                user.removeDepositRef(1);
                final ItemStack a = new ItemStack(Material.GOLDEN_APPLE,1);
                ItemUtil.giveItems(Collections.singletonList(a),player);
                final ItemMeta meta = itemStack.getItemMeta();
                final List<String> lore = meta.getLore();
                lore.set(1,Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Aktualnie posiadasz w schowku: " + ChatColor.YELLOW + UnderLined + user.getDepositRef())));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2  + "Nie mozesz tego zrobic jezeli nie masz przynajmniej jednego przedmiotu tego rodzaju w schowku");
            }
        });
        inventoryGUI.setItem(16,pink.build(),null);
        inventoryGUI.setItem(17,lightblue.build(),null);
        inventoryGUI.setItem(18,magneta.build(),null);
        inventoryGUI.setItem(19,lightblue.build(),null);
        inventoryGUI.setItem(20,magneta.build(),null);
        inventoryGUI.setItem(21,blue.build(),null);
        final ItemBuilder hopper = new ItemBuilder(Material.HOPPER).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.DARK_RED + UnderLined + "WYPLAC DO LIMITU" + SpecialSigns + " <<-")));
        inventoryGUI.setItem(22,hopper.build(),(player1, inventory, i, itemStack) -> {
            final int lk = ItemStackUtil.getAmountOfItem(Material.GOLDEN_APPLE, player1,(short)1);
            final int lr = ItemStackUtil.getAmountOfItem(Material.GOLDEN_APPLE, player1, (short)0);
            final int lp = ItemStackUtil.getAmountOfItem(Material.ENDER_PEARL, player1,(short)0);
            final int amountkox = CoreConfig.DEPOSITMANAGER_LIMITKOX - lk;
            final int amountref = CoreConfig.DEPOSITMANAGER_LIMITREF - lr;
            final int amountpearl = CoreConfig.DEPOSITMANAGER_LIMITPEARL - lp;
            final List<ItemStack> items = new ArrayList<>();
            if(amountkox > 0 && user.getDepositKox() >= amountkox) {
                items.add(new ItemStack(Material.GOLDEN_APPLE, amountkox, (short) 1));
                user.removeDepositKox(amountkox);
            }else{
                Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + MainColor + "Masz juz limit koxow lub nie posiadasz tyle w" + ImportantColor + " schowku"));
            }
            if(amountref > 0 && user.getDepositRef() >= amountref) {
                items.add(new ItemStack(Material.GOLDEN_APPLE, amountref));
                user.removeDepositRef(amountref);
            }else{
                Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + MainColor + "Masz juz limit refili lub nie posiadasz tyle w" + ImportantColor + " schowku"));
            }
            if(amountpearl > 0 && user.getDepositPearl() >= amountpearl){
                items.add(new ItemStack(Material.ENDER_PEARL,amountpearl));
                user.removeDepositPearl(amountpearl);
            }else{
                Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + MainColor + "Masz juz limit perel lub nie posiadasz tyle w" + ImportantColor + " schowku"));
            }
            ItemUtil.giveItems(items,player);
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + MainColor + "Wyplacono przedmioty ze schowka do " + ImportantColor + "limitu "));
            player.closeInventory();
        });
        inventoryGUI.setItem(23,blue.build(),null);
        inventoryGUI.setItem(24,magneta.build(),null);
        inventoryGUI.setItem(25,lightblue.build(),null);
        inventoryGUI.setItem(26,magneta.build(),null);

        inventoryGUI.openInventory(player);
    }
}
