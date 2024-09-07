package pl.blackwater.hardcore.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.storage.MysteryStorage;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;

public class MysteryEditInventory implements Colors {


    public static void openMenu(Player p){
        InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.replaceString(Util.fixColor(SpecialSigns + "->> " + MainColor + "MysteryBox: Preview")), 6);
        int i = 0;
        for(ItemStack is : MysteryStorage.getMysteryDrops()){
            final ItemStack a = is.clone();
            final ItemMeta meta = a.getItemMeta();
            meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kliknij, aby usunac ten " + ImportantColor + UnderLined + "przedmiot " + MainColor + " z " + ImportantColor + "mysterybox'a"))));
            a.setItemMeta(meta);
            inventoryGUI.setItem(i, a, ((player, inventory, i4, itemStack) -> {
                String convert = Core.getMysteryStorage().stringConvert(is);
                Core.getMysteryStorage().removeToListField("mystery.storage", convert);
                MysteryStorage.getMysteryDrops().remove(is);
                player.closeInventory();
                openMenu(player);
            }));
            i++;
        }
        ItemStack addItem = new ItemBuilder(Material.STAINED_CLAY, 1, (byte)5).setTitle(Util.replaceString(Util.fixColor(SpecialSigns + "->> " + ImportantColor + "Dodaj przedmiot"))).build();
        inventoryGUI.setItem(53, addItem, ((player, inventory, i1, itemStack) -> {
            InventoryGUI addingGUI = new InventoryGUI(Core.getInstance(), Util.replaceString(Util.fixColor(SpecialSigns + "->> " + MainColor + "MysteryBox: Adding Item")), 6);
            int index = 0;
            for (ItemStack content : player.getInventory().getContents()) {
                if (content != null && !content.getType().equals(Material.AIR)) {
                    final ItemStack item = content.clone();
                    final ItemMeta meta = item.getItemMeta();
                    meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kliknij, aby dodac ten " + ImportantColor + UnderLined + "przedmiot " + MainColor + " do " + ImportantColor + "mysterybox'a"))));
                    item.setItemMeta(meta);
                    addingGUI.setItem(index, item, (player1, inventory1, i2, itemStack1) -> {
                        Core.getMysteryStorage().addToListField("mystery.storage", Core.getMysteryStorage().stringConvert(content));
                        MysteryStorage.getMysteryDrops().add(content);
                        p.closeInventory();
                        openMenu(p);
                    });
                    index++;
                }
            }
            addingGUI.openInventory(p);
        }));
        inventoryGUI.openInventory(p);
    }
}
