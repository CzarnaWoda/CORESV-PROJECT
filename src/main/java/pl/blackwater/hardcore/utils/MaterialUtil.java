package pl.blackwater.hardcore.utils;

import org.bukkit.Material;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

public class MaterialUtil {

	public static Material getMaterial(String materialName) {
        Material returnMaterial;
        if (Util.isInteger(materialName)) {
            int id = Integer.parseInt(materialName);
            returnMaterial = Material.getMaterial(id);
        }
        else {
            returnMaterial = Material.matchMaterial(materialName);
        }
        return returnMaterial;
    }

    public static ItemBuilder getStainedGlassPane(final short s) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE,1,s).setTitle(Util.fixColor("&2"));
    }
    public static void setCraftingBackground(InventoryGUI inventoryGUI){
        inventoryGUI.setItem(0, MaterialUtil.getStainedGlassPane((short)6).build(), null);
        inventoryGUI.setItem(1, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(2, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(3, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(4, MaterialUtil.getStainedGlassPane((short)2).build(), null);
        inventoryGUI.setItem(5, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(6, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(7, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(8, MaterialUtil.getStainedGlassPane((short)6).build(), null);
        inventoryGUI.setItem(9, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(13, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(14, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(15, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(16, MaterialUtil.getStainedGlassPane((short)3).build(), null);
        inventoryGUI.setItem(17, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(18, MaterialUtil.getStainedGlassPane((short)2).build(), null);
        inventoryGUI.setItem(22, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(24, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(25, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(26, MaterialUtil.getStainedGlassPane((short)2).build(), null);
        inventoryGUI.setItem(27, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(31, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(33, MaterialUtil.getStainedGlassPane((short)9).build(), null);
        inventoryGUI.setItem(34, MaterialUtil.getStainedGlassPane((short)3).build(), null);
        inventoryGUI.setItem(35, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(36, MaterialUtil.getStainedGlassPane((short)6).build(), null);
        inventoryGUI.setItem(37, MaterialUtil.getStainedGlassPane((short)1).build(), null);
        inventoryGUI.setItem(38, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(39, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(40, MaterialUtil.getStainedGlassPane((short)2).build(), null);
        inventoryGUI.setItem(41, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(42, MaterialUtil.getStainedGlassPane((short)4).build(), null);
        inventoryGUI.setItem(43, MaterialUtil.getStainedGlassPane((short)1).build(), null);
    }

}
