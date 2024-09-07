package pl.blackwater.hardcore.inventories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Kit;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.ItemManager;
import pl.blackwater.hardcore.utils.StringUtil;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Map;

public class KitInventory implements Colors {

    public static void openKitInventory(Player player){
        final InventoryGUI kitInventory = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "|->> " + MainColor + "Wybor " + ImportantColor + "zestawu" + SpecialSigns + "<<-|")),1);
        int index = 0;
        final User user = Core.getUserManager().getUser(player);
        for(Kit kit : Core.getKitManager().getKits().values()){
            final int kittime = (int) (user.getKitTimes().get(kit.getTimeKey()) + kit.getTime() - System.currentTimeMillis());
            ItemBuilder itemBuilder = new ItemBuilder(kit.getMenuItem()).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zestaw " + SpecialSigns + "* " + ImportantColor + kit.getId().toUpperCase())))
                    .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Zestaw zawiera: ")));
            kit.getItems().forEach(itemStack -> itemBuilder.addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    -|>> " + MainColor + ItemManager.get(itemStack.getType()) + SpecialSigns + "x" +  ImportantColor + itemStack.getAmount() + (itemStack.getEnchantments().size() > 0 ? SpecialSigns
                    + " (" + ChatColor.GOLD + onlyEnchantsLevel(itemStack.getEnchantments()) + SpecialSigns + ")" : "")))));
            itemBuilder.addLore("");
            itemBuilder.addLore(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Dostepnosc globalna: " + (kit.isEnable() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%")));
            itemBuilder.addLore(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Dostepnosc rangi: " + (player.hasPermission("core.kit." + kit.getId().toLowerCase()) ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%")));
            itemBuilder.addLore(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Dostepnosc czasu: " + (user.getKitTimes().get(kit.getTimeKey()) + kit.getTime() <= System.currentTimeMillis() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%, dostepny za " + StringUtil.getDurationBreakdown(kittime))));
            kitInventory.setItem(index, itemBuilder.build(), (p, inventory, i, itemStack) -> {
                if(p.hasPermission("core.kit." + kit.getId().toLowerCase()) && user.getKitTimes().get(kit.getTimeKey()) + kit.getTime() <= System.currentTimeMillis() && kit.isEnable()) {
                    System.out.println(user.getKitTimes().get(kit.getTimeKey()) + kit.getTime());
                    System.out.println(System.currentTimeMillis());
                    InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "|->> " + MainColor + "Zestaw " + ImportantColor + kit.getId().toUpperCase() + SpecialSigns + " <<-|")), 6);
                    ItemBuilder kititem = new ItemBuilder(kit.getMenuItem()).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + "KIT " + kit.getId().toUpperCase() + SpecialSigns + " <<-")));
                    inv.setItem(4,kititem.build(),null);
                    int index1 = 18;
                    for (ItemStack itemStack1 : kit.getItems()) {
                        inv.setItem(index1, itemStack1, null);
                        index1++;
                    }
                    ItemBuilder done = new ItemBuilder(Material.STAINED_CLAY, 1, (short)5).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "|->> " + MainColor + "Kliknij aby " + ImportantColor + "odebrac zestaw")));
                    inv.setItem(inv.get().getSize() - 1 , done.build() ,  (player1, inventory1, i1, itemStack1) -> {
                        p.closeInventory();
                        user.getKitTimes().set(kit.getTimeKey(), System.currentTimeMillis());
                        ItemUtil.giveItems(kit.getItems(), p);
                    });
                    inv.openInventory(p);
                }
            });
            index++;
        }
        kitInventory.openInventory(player);
    }
    public static String onlyEnchantsLevel(Map<Enchantment, Integer> map){
        int i = 0;
        StringBuilder out = new StringBuilder();
        for(int ints : map.values()){
            i++;
            out.append(ints).append(i < map.size() ? "/" : "");
        }
        return out.toString();
    }
}
