package pl.blackwater.hardcore.inventories;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CustomItemConfig;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwater.hardcore.utils.MaterialUtil;
import pl.blackwaterapi.gui.actions.IAction;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;
import java.util.List;

public class CustomCraftingInventory implements Colors {

    public InventoryGUI craftingInventory;

    public CustomCraftingInventory(){
        craftingInventory = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptury na " + ImportantColor + "serwerze " + SpecialSigns + "<<-")),1);
        int i = buildInventory();
        CoreUtil.sendConsoleMessage("Loaded " + i + " recipes to CustomCraftingInventory");
    }
    public int buildInventory(){
        int recipe = 0;
        if(CustomItemConfig.ITEMS_STONIARKA_ENABLE) {
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getGenerator(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "Stoniarke " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_STONIARKA_CRAFTING, CustomItemConfig.ITEMS_STONIARKA_SIMPLECRAFTING, Core.getCustomItemManager().getGenerator()));
            recipe++;
        }
        if(CustomItemConfig.ITEMS_SANDFARMER_ENABLE) {
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getSandfarmer(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "SandFarmer " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_SANDFARMER_CRAFTING, CustomItemConfig.ITEMS_SANDFARMER_SIMPLECRAFTING, Core.getCustomItemManager().getSandfarmer()));
            recipe++;
        }
        if(CustomItemConfig.ITEMS_BOYFARMER_ENABLE) {
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getBoyfarmer(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "BoyFarmer " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_BOYFARMER_CRAFTING, CustomItemConfig.ITEMS_BOYFARMER_SIMPLECRAFTING, Core.getCustomItemManager().getBoyfarmer()));
            recipe++;
        }
        if(CustomItemConfig.ITEMS_AIRGENERATOR_ENABLE) {
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getAirgenerator(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "Kopacz Fosy " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_AIRGENERATOR_CRAFTING, CustomItemConfig.ITEMS_AIRGENERATOR_SIMPLECRAFTING, Core.getCustomItemManager().getAirgenerator()));
            recipe++;
        }
        if(CustomItemConfig.ITEMS_SPECIALSTONE_ENABLE){
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getSpecialstone(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "SpecialStone " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_SPECIALSTONE_CRAFTING, CustomItemConfig.ITEMS_SPECIALSTONE_SIMPLECRAFTING, Core.getCustomItemManager().getSpecialstone()));
            recipe++;
        }
        if(CustomItemConfig.ITEMS_GOLDENAPPLE_ENABLE){
            craftingInventory.setItem(recipe, Core.getCustomItemManager().getGoldenapple(), new CraftingInventoryAction(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Receptura na" + ImportantColor + "Zlote Jablko " + SpecialSigns + "<<-")), CustomItemConfig.ITEMS_GOLDENAPPLE_CRAFTING, CustomItemConfig.ITEMS_GOLDENAPPLE_SIMPLECRAFTING, Core.getCustomItemManager().getGoldenapple()));
        }
        return recipe;
    }
    public boolean openInventory(Player player){
        InventoryGUI inv = craftingInventory;
        inv.openInventory(player);
        return true;
    }

}
@Getter
class CraftingInventoryAction implements IAction,Colors {

    private String title;
    private List<Integer> materials;
    private String simpleCrafting;
    private ItemStack craftingItem;

    public CraftingInventoryAction(String title,List<Integer> materials, String simpleCrafting, ItemStack craftingItem){
        this.title = title;
        this.materials = materials;
        this.simpleCrafting = simpleCrafting;
        this.craftingItem = craftingItem;
    }

    @Override
    public void execute(Player player, Inventory inventory, int itemint, ItemStack itemStack) {
        final InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(getTitle())),5);
        MaterialUtil.setCraftingBackground(inventoryGUI);
        final ItemBuilder autoCraft = new ItemBuilder(Material.WORKBENCH,1).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.BLUE + "AUTOMATYCZNE CRAFTOWANIE")));
        final ItemBuilder back = new ItemBuilder(Material.STAINED_CLAY,1,(short)14).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ChatColor.DARK_RED + "Powrot")));
        inventoryGUI.setItem(32,autoCraft.build(), (p, inv, index, item) -> {
            List<ItemStack> items = ItemUtil.getItems(simpleCrafting,1);
            if(ItemUtil.checkAndRemove(items, player)){
                ItemUtil.giveItems(Collections.singletonList(craftingItem),player);
            }else{
                Util.sendMsg(player, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wymaganych przedmiotow " + SpecialSigns + "(" + WarningColor + ItemUtil.getItems(items) + SpecialSigns + ")")));
            }
        });
        inventoryGUI.setItem(44,back.build(), (p, inv, index, item) -> {
            p.closeInventory();
            p.openInventory(inventory);
        });
        int index = 0;
        for (int i = 10; index < materials.size(); i++) {
            if (i == 13 || i == 22 || i == 31) {
                i = i + 6;
            }
            Material mat = Material.getMaterial(materials.get(index));
            ItemBuilder a = new ItemBuilder(mat).setTitle(Util.fixColor("&7"));
            if(craftingItem.equals(Core.getCustomItemManager().getSpecialstone()))
                a.setAmount(64);
            inventoryGUI.setItem(i, a.build(),null);
            index++;
        }
        inventoryGUI.setItem(23,craftingItem,null);
        player.closeInventory();
        inventoryGUI.openInventory(player);
    }
}
