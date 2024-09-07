package pl.blackwater.hardcore.guilds.inventories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.GuildPermission;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;
import java.util.List;

public class ProfilesInventory implements Colors {

    private static final ThreadLocal<HashMap<String, Material>> materials = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<HashMap<String, String>> titles = ThreadLocal.withInitial(HashMap::new);
    static {
        materials.get().put("buildPermission", Material.STONE);
        materials.get().put("destroyPermission", Material.DIAMOND_PICKAXE);
        materials.get().put("openChestPermission", Material.CHEST);
        materials.get().put("openFurnacesPermission", Material.FURNACE);
        materials.get().put("destroyGeneratorPermission", Material.ENDER_STONE);
        materials.get().put("openDepositPermission", Material.GOLD_BLOCK);
        materials.get().put("kickPlayersPermission", Material.BARRIER);
        materials.get().put("togglePvPPermission", Material.DIAMOND_SWORD);
        materials.get().put("managePermission", Material.BOOK);
        materials.get().put("manageProfilesPermission", Material.EMERALD);
        materials.get().put("buildTnTPermission", Material.TNT);
        materials.get().put("destroyObsidianPermission", Material.OBSIDIAN);

        titles.get().put("buildPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Budowanie na terenie gildii" + SpecialSigns + " <<-")));
        titles.get().put("destroyPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Niszczenie na terenie gildii" + SpecialSigns + " <<-")));
        titles.get().put("openChestPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Otwieranie skrzynek terenie gildii" + SpecialSigns + " <<-")));
        titles.get().put("openFurnacesPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Otwieranie piecy na terenie gildii" + SpecialSigns + " <<-")));
        titles.get().put("destroyGeneratorPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Niszczenie nad stoniarkami na terenie gildii" + SpecialSigns + " <<-")));
        titles.get().put("openDepositPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Otwieranie depozytu" + SpecialSigns + " <<-")));
        titles.get().put("kickPlayersPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Wyrzucanie graczy z gildii" + SpecialSigns + " <<-")));
        titles.get().put("togglePvPPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Zmienianie statusu PvP w gildii" + SpecialSigns + " <<-")));
        titles.get().put("managePermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Dostep do zarzadzania gildia" + SpecialSigns + " <<-")));
        titles.get().put("manageProfilesPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Dostep do zarzadzania profilami" + SpecialSigns + " <<-")));
        titles.get().put("buildTnTPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Budowanie na terenie gildii (TNT)" + SpecialSigns + " <<-")));
        titles.get().put("destroyObsidianPermission", Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Niszczenie na terenie gildii (OBSIDIAN)" + SpecialSigns + " <<-")));

    }
    public static void openMenu(Guild guild, Player player){
        final InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.replaceString(SpecialSigns + "->> " + MainColor + "Profile graczy w " + ImportantColor + "gildii " + SpecialSigns + " <<-"), 5);
        int index = 0;
        for(Member member : Core.getMemberManager().getMembers(guild)) {
            if (!member.getPosition().equals(Position.LIDER)) {
                final ItemStack item = ItemUtil.getPlayerHead(member.getUser().getLastName());
                final ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Profil gracza " + ImportantColor + member.getUser().getLastName())));
                item.setItemMeta(meta);
                inv.setItem(index, item, (p, inventory, integer, itemStack) -> {
                    final InventoryGUI i = new InventoryGUI(Core.getInstance(), Util.replaceString(SpecialSigns + "->> " + MainColor + "Profile graczy w " + ImportantColor + "gildii " + SpecialSigns + " <<-"), 3);
                    int index2 = 0;
                    for (GuildPermission permission : member.getPermissions().values()) {
                        ItemBuilder ib = new ItemBuilder(materials.get().get(permission.getName()));
                        ib.setTitle(titles.get().get(permission.getName()));
                        ib.addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Status: " + (permission.isStatus() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                        .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Nadany przez: " + permission.getGetBy())))
                        .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Czas nadania: " + Util.getDate(permission.getGetTime()))));
                        i.setItem(index2, ib.build(), (p1, inventory1, integer1, itemStack1) -> {
                            permission.toggle(p1.getDisplayName(),System.currentTimeMillis());
                            final ItemMeta meta1 = itemStack1.getItemMeta();
                            final List<String> loreList = meta1.getLore();
                            loreList.set(0, Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Status: " + (permission.isStatus() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))));
                            loreList.set(1, Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Nadany przez: " + permission.getGetBy())));
                            loreList.set(2, Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Czas nadania: " + Util.getDate(permission.getGetTime()))));
                            meta1.setLore(loreList);
                            itemStack1.setItemMeta(meta1);
                        });
                        index2++;
                    }
                    p.closeInventory();
                    i.openInventory(p);
                });
                index++;
            }
        }
        inv.openInventory(player);
    }
}
