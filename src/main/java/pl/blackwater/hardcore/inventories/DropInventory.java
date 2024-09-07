package pl.blackwater.hardcore.inventories;

import net.lightshard.itemcases.ItemCases;
import net.lightshard.itemcases.cases.ItemCase;
import net.lightshard.itemcases.userinterface.preset.Uis;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.drop.data.Drop;
import pl.blackwater.hardcore.drop.data.drops.RandomDropData;
import pl.blackwater.hardcore.drop.managers.DropManager;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.enums.InventoryStyleType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EventManager;
import pl.blackwater.hardcore.settings.CustomItemConfig;
import pl.blackwater.hardcore.storage.MysteryStorage;
import pl.blackwater.hardcore.utils.MaterialUtil;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.MathUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DropInventory implements Colors {
    public DropInventory(@NotNull InventoryStyleType type){
        /*switch (type){
            case MCKOX:
                break;
            case CORESV:

                break;
            default:
                Logger.warning("Error, dropIventoryType wasnt chose!");
        }*/
    }
    public void openMcKoxDropMenu(Player p){
        final User u = Core.getUserManager().getUser(p);
        InventoryGUI mainInventory = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->>" + MainColor + " MCKOX " + ImportantColor + "DROP")),3);
        final ItemBuilder turbodrop = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + ImportantColor + UnderLined + "TurboDrop" + SpecialSigns + " <<")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Tryb " + WarningColor + "GRACZ" + SpecialSigns + " *")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (u.isTurboDrop() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (u.isTurboDrop() ? "Aktywny do: " + ImportantColor + Util.getDate(u.getTurboDropTime()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int) ((int)(u.getTurboDropTime() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (u.getTurboDropTime() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(u.getTurboDropTime()))))))
                .addLore(Util.fixColor(""))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Tryb " + WarningColor + "ALL" + SpecialSigns + " *")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (EventManager.isOnTurboDrop() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (EventManager.isOnTurboDrop() ? "Aktywny do: " + ImportantColor + Util.getDate(EventManager.getTurbodrop_time()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int)((int)(EventManager.getTurbodrop_time() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (EventManager.getTurbodrop_time() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(EventManager.getTurbodrop_time()))))));
        final ItemBuilder turboexp = new ItemBuilder(Material.EXP_BOTTLE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + ImportantColor + UnderLined + "TurboExp" + SpecialSigns + " <<")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Tryb " + WarningColor + "GRACZ" + SpecialSigns + " *")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (u.isTurboExp() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (u.isTurboExp() ? "Aktywny do: " + ImportantColor + Util.getDate(u.getTurboExpTime()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int) ((int)(u.getTurboExpTime() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (u.getTurboExpTime() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(u.getTurboExpTime()))))))
                .addLore(Util.fixColor(""))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Tryb " + WarningColor + "ALL" + SpecialSigns + " *")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (EventManager.isOnTurboExp() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (EventManager.isOnTurboExp() ? "Aktywny do: " + ImportantColor + Util.getDate(EventManager.getTurboexp_time()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int)((int)(EventManager.getTurboexp_time() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (EventManager.getTurboexp_time() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(EventManager.getTurboexp_time()))))));
        final ItemBuilder events = new ItemBuilder(Material.GOLD_BLOCK).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + ImportantColor + UnderLined + "Eventy" + SpecialSigns + " <<")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Event: " + ChatColor.BLUE + "DROP")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (EventManager.isOnEventDrop() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (EventManager.isOnEventDrop() ? "Aktywny do: " + ImportantColor + Util.getDate(EventManager.getDrop_time()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int)((int)(EventManager.getDrop_time() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (EventManager.getDrop_time() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(EventManager.getDrop_time()))))))
                .addLore(Util.fixColor(Util.replaceString((EventManager.isOnEventDrop() ? SpecialSigns + "  * " + MainColor + "Mnoznik: x" + ImportantColor + UnderLined + Config.EVENT_DROP : ""))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + " * " + MainColor + "Event: " + ChatColor.BLUE + "EXP")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Aktywny: " + (EventManager.isOnEventExp() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + (EventManager.isOnEventExp() ? "Aktywny do: " + ImportantColor + Util.getDate(EventManager.getExp_time()) + SpecialSigns + " (" + WarningColor + Util.secondsToString((int)((int)(EventManager.getExp_time() - System.currentTimeMillis()) / 1000L)) + SpecialSigns + ")" : "Ostatnio aktywny: " + (EventManager.getExp_time() == 0L ? ChatColor.DARK_RED + "Nigdy" : ImportantColor + Util.getDate(EventManager.getExp_time()))))))
                .addLore(Util.fixColor(Util.replaceString((EventManager.isOnEventExp() ? SpecialSigns + "  * " + MainColor + "Mnoznik: x" + ImportantColor + UnderLined + Config.EVENT_EXP : ""))))
                .addLore(Util.fixColor(Util.replaceString("&a")));
        mainInventory.setItem(14,events.build(),null);
        mainInventory.setItem(15, turbodrop.build(),null);
        mainInventory.setItem(16,turboexp.build(),null);

        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short)11);
        final ItemBuilder magnet = MaterialUtil.getStainedGlassPane((short)2);
        final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short)3);
        mainInventory.setItem(0,blue.build(),null);
        mainInventory.setItem(1,magnet.build(),null);
        mainInventory.setItem(2,lightblue.build(),null);
        mainInventory.setItem(3,blue.build(),null);
        mainInventory.setItem(4,lightblue.build(),null);
        mainInventory.setItem(5,blue.build(),null);
        mainInventory.setItem(6,lightblue.build(),null);
        mainInventory.setItem(7,magnet.build(),null);
        mainInventory.setItem(8,blue.build(),null);
        mainInventory.setItem(9,magnet.build(),null);

        final ItemBuilder stone = new ItemBuilder(Material.STONE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z " + ChatColor.BLUE + "STONE")));
        mainInventory.setItem(10,stone.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            openMCKOXStoneDrop(player);
        });
        final ItemBuilder chest = new ItemBuilder(Material.CHEST).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z " + ChatColor.GOLD + "SKRZYNEK")));
        mainInventory.setItem(11,chest.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            ItemCase itemCase = ItemCases.getInstance().getCaseManager().fromName("mckox");
            if(itemCase != null){
                Uis.get(player).setCurrentlyPreviewing(itemCase);
                Uis.get(player).setPage(1);
                Uis.REWARDS_PREVIEW.show(player);
            }
        });
        final ItemBuilder cobblex = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_SPECIALSTONE_ITEM))
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z " + ChatColor.YELLOW + "COBBLEX")));
        mainInventory.setItem(12,cobblex.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            openCobblexInventory(player);
        });
        final ItemBuilder pandora = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_MYSTERYBOX_ITEM))
                .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z " + ChatColor.LIGHT_PURPLE + "PANDORY")));
        mainInventory.setItem(13,pandora.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            openPandoraInventory(player);
        });
        mainInventory.setItem(17,magnet.build(),null);
        mainInventory.setItem(18,blue.build(),null);
        mainInventory.setItem(19,magnet.build(),null);
        mainInventory.setItem(20,lightblue.build(),null);
        mainInventory.setItem(21,blue.build(),null);
        mainInventory.setItem(22,lightblue.build(),null);
        mainInventory.setItem(23,blue.build(),null);
        mainInventory.setItem(24,lightblue.build(),null);
        mainInventory.setItem(25,magnet.build(),null);
        mainInventory.setItem(26,blue.build(),null);
        mainInventory.openInventory(p);
    }
    private void openCobblexInventory(Player player){
        final InventoryGUI cobblexInventory = new InventoryGUI(Core.getInstance(),Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z COBBLEX " + SpecialSigns + "<<-")),6);
        cobblexInventory.setItem(4,Core.getCustomItemManager().getSpecialstone(),null);
        int index = 18;
        for(ItemStack item : Core.getSpecialStoneManager().getSpecialStoneItems()){
            cobblexInventory.setItem(index,item,null);
            index++;
        }
        final ItemBuilder back = new ItemBuilder(Material.WOOL,1,(short)14).setTitle(Util.replaceString(SpecialSigns + "* " + WarningColor_2 + "Powrot" + SpecialSigns + " *"));
        cobblexInventory.setItem(cobblexInventory.get().getSize() - 1, back.build(), (p, arg1, arg2, arg3) -> {
            p.closeInventory();
            openMcKoxDropMenu(p);
        });
        cobblexInventory.openInventory(player);
    }
    private void openPandoraInventory(Player player){
        InventoryGUI mysteryBoxInventory = new InventoryGUI(Core.getInstance(),Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "DROP Z PANDORY " + SpecialSigns + "<<-")),6);
        mysteryBoxInventory.setItem(4,Core.getCustomItemManager().getMysterybox(),null);
        int index = 18;
        for(ItemStack item : MysteryStorage.getMysteryDrops()){
            mysteryBoxInventory.setItem(index,item,null);
            index++;
        }
        final ItemBuilder back = new ItemBuilder(Material.WOOL,1,(short)14).setTitle(Util.replaceString(SpecialSigns + "* " + WarningColor_2 + "Powrot" + SpecialSigns + " *"));
        mysteryBoxInventory.setItem(mysteryBoxInventory.get().getSize() - 1, back.build(), (p, arg1, arg2, arg3) -> {
            p.closeInventory();
            openMcKoxDropMenu(p);
        });
        mysteryBoxInventory.openInventory(player);
    }
    private void openMCKOXStoneDrop(Player player){
        final InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "STONE DROP" + SpecialSigns + " <<-")), 4);
        int position = 0;
        final User user = Core.getUserManager().getUser(player);
        for (Drop cbl : RandomDropData.getDrops()) {
            ItemBuilder itemBuilder = new ItemBuilder(cbl.getWhat().getType(), 1).setTitle(Util.fixColor(ImportantColor + cbl.getName().toUpperCase())).addLores(Arrays.asList(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Szansa na drop: " + ImportantColor + ((EventManager.isOnEventDrop() ? MathUtil.round((cbl.getChance() + user.getBonusDrop())*Config.EVENT_DROP,2) : MathUtil.round(cbl.getChance() + user.getBonusDrop(),2)) + "%") + (Core.getUserManager().getUser(player).isPremium() ? (MainColor + " + " + ImportantColor + cbl.getChanceVIP() + "% " + SpecialSigns + "(" + MainColor + "Premium" + SpecialSigns + ")") : "")) + (MainColor + " + " + ImportantColor + MathUtil.round(user.getLevel()*cbl.getChanceBonus(),2) + "% " + SpecialSigns + "(" + MainColor + "BONUSDROP" + SpecialSigns + ")") + (EventManager.isOnTurboDrop() || user.isTurboDrop() ? (MainColor + " + " + ImportantColor + cbl.getChanceTurboDrop() + "% " + SpecialSigns + "(" + MainColor + "TurboDrop" + SpecialSigns + ")") : "")), Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Dzialanie zaklecia fortuny: " + (cbl.isFortune() ? ChatColor.GREEN + "Tak" : ChatColor.DARK_RED + "Nie"))), Util.fixColor(Util.replaceString(SpecialSigns + "* " +  MainColor + "Aktywny: " + (cbl.isDisabled(player.getUniqueId()) ? ChatColor.DARK_RED + "Nie" : ChatColor.GREEN + "Tak"))), "", Util.fixColor(Util.replaceString(SpecialSigns + ">>" +  MainColor + " Pamietaj! Ranga " +  ImportantColor + "PREMIUM" + MainColor + " posiada wiekszy drop o " + ImportantColor + Config.DROP_VIP_DROP + "%" + MainColor + "!"))));
            inv.setItem(position, itemBuilder.build(), (p, arg1, arg2, item) -> {
                final ItemMeta meta = item.getItemMeta();
                final Drop d = RandomDropData.getDropByName(ChatColor.stripColor(Util.fixColor(meta.getDisplayName())));
                if(d != null) {
                    d.changeStatus(p.getUniqueId());
                    final List<String> lore = meta.getLore();
                    lore.set(2, SpecialSigns + "* " +  MainColor + "Aktywny: " + (d.isDisabled(p.getUniqueId()) ? ChatColor.DARK_RED + "Nie" : ChatColor.GREEN + "Tak"));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            });
            position++;
        }
        ItemBuilder exp = new ItemBuilder(Material.EXP_BOTTLE).setTitle(Util.fixColor(Util.replaceString(ImportantColor + "Doswiadczenie"))).addLore(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Kamien: " + ImportantColor + (EventManager.isOnEventExp() ? DropManager.getExp(Material.STONE) * Config.EVENT_DROP : DropManager.getExp(Material.STONE)) + MainColor + (user.isPremium() ? " + " + ImportantColor + Config.DROP_VIP_EXP + SpecialSigns + " (" + MainColor + "PREMIUM" + SpecialSigns + ")" : "") + (user.isTurboExp() || EventManager.isOnTurboExp() ? MainColor + " + " + ImportantColor + Config.DROP_TURBO_EXP + SpecialSigns + " (" + MainColor + "TurboEXP" + SpecialSigns + ")" : ""))));
        ItemBuilder cb = new ItemBuilder(Material.COBBLESTONE).setTitle(Util.fixColor(ImportantColor + "Cobblestone")).addLore(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Aktywny: " + (RandomDropData.isNoCobble(player.getUniqueId()) ? ChatColor.DARK_RED + "Nie" : ChatColor.GREEN + "Tak"))));
        ItemBuilder ch1 = new ItemBuilder(Material.ENCHANTED_BOOK).setTitle(Util.fixColor(ImportantColor + "Fortune I")).addLore(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Szansa: " + ImportantColor + "10%")));
        ItemBuilder ch2 = new ItemBuilder(Material.ENCHANTED_BOOK).setTitle(Util.fixColor(ImportantColor + "Fortune II")).addLore(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Szansa: " + ImportantColor + "20%")));
        ItemBuilder ch3 = new ItemBuilder(Material.ENCHANTED_BOOK).setTitle(Util.fixColor(ImportantColor + "Fortune III")).addLore(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Szansa: " + ImportantColor + "30%")));
        inv.setItem(27, cb.build(), (p, arg1, arg2, item) -> {
            RandomDropData.changeNoCobble(p.getUniqueId());
            final List<String> lore = Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "* " + MainColor + "Aktywny: " + (RandomDropData.isNoCobble(p.getUniqueId()) ? ChatColor.DARK_RED + "Nie" : ChatColor.GREEN + "Tak"))));
            final ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
        });
        final int size = inv.get().getSize();
        inv.setItem(size - 6, ch1.build(),null);
        inv.setItem(size - 5, ch2.build(),null);
        inv.setItem(size - 4, ch3.build(),null);
        final ItemBuilder back = new ItemBuilder(Material.WOOL,1,(short)14).setTitle(Util.replaceString(SpecialSigns + "* " + WarningColor_2 + "Powrot" + SpecialSigns + " *"));
        inv.setItem(size - 1, back.build(), (p, arg1, arg2, arg3) -> {
            p.closeInventory();
            openMcKoxDropMenu(player);
        });
        inv.setItem(28,exp.build(),null);
        player.openInventory(inv.get());
    }
}
