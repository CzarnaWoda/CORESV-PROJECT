package pl.blackwater.hardcore.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.PlayerClassType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

public class PlayerClassInventory implements Colors {


    public void openPlayerClassInventory(Player player) {
        final InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Wybierz swoja " + ImportantColor + "klase")), 1);

        final ItemBuilder warrior = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa " + ImportantColor + UnderLined + "wojownik" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString("")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa posiada: ")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Po zabiciu" + ImportantColor + " gracza " + MainColor + "otrzymuje efekty:")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "STRENGHT 1 na 15 sekund")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Po zjedzeniu" + ImportantColor + " koxa " + MainColor + "otrzymuje efekty:")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "SPEED 2 na 6 sekund")));
        final ItemBuilder archer = new ItemBuilder(Material.BOW).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa " + ImportantColor + UnderLined + "lucznik" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString("")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa posiada: ")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Mozliwosc " + ImportantColor + " uzywania luku z punch" + MainColor + ":")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "Luku z punch mozna uzywac co 10 sekund")));
        final ItemBuilder digger = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa " + ImportantColor + UnderLined + "gornik" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString("")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Klasa posiada: ")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  * " + MainColor + "Dodatkowe mozliwosci " + ImportantColor + " podczas kopania" + MainColor + ":")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "0.33% na HASTE II na 90 sekund")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "0.15% na TURBODROP na 10 minut")));

        inv.setItem(1,warrior.build(),(p, inventory, i, item) -> {
            final User user = Core.getUserManager().getUser(p);
            if(user.getPlayerClassType().equals(PlayerClassType.UNKNOWN)){
                user.setPlayerClassType(PlayerClassType.WARRIOR);
                p.closeInventory();
            }else{
                p.closeInventory();
                Util.sendMsg(p, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Posiadasz juz klase postaci")));
            }
        });
        inv.setItem(3,archer.build(),(p, inventory, i, item) -> {
            final User user = Core.getUserManager().getUser(p);
            if(user.getPlayerClassType().equals(PlayerClassType.UNKNOWN)){
                user.setPlayerClassType(PlayerClassType.ARCHER);
                p.closeInventory();
            }else{
                p.closeInventory();
                Util.sendMsg(p, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Posiadasz juz klase postaci")));
            }
        });
        inv.setItem(5,digger.build(),(p, inventory, i, item) -> {
            final User user = Core.getUserManager().getUser(p);
            if(user.getPlayerClassType().equals(PlayerClassType.UNKNOWN)){
                user.setPlayerClassType(PlayerClassType.DIGGER);
                p.closeInventory();
            }else{
                p.closeInventory();
                Util.sendMsg(p, Util.fixColor(Util.replaceString(WarningColor + "Blad: " + WarningColor_2 + "Posiadasz juz klase postaci")));
            }
        });
        inv.openInventory(player);
    }
}
