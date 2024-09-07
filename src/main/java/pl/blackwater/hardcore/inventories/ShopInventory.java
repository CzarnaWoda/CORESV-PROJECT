package pl.blackwater.hardcore.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Effect;
import pl.blackwater.hardcore.data.Shop;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.InventoryStyleType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.ItemManager;
import pl.blackwater.hardcore.utils.MaterialUtil;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemStackUtil;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;

public class ShopInventory implements Colors {
    public ShopInventory(InventoryStyleType e){
    }
    public void openMain(Player p){
        InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Sklep " + ImportantColor + UnderLined + "CORESV" + SpecialSigns + " <<-")),1);
        final ItemBuilder gold = new ItemBuilder(Material.GOLD_NUGGET).setTitle("§1");
        final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short) 3);
        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short) 11);
        inv.setItem(0, gold.build(), null);
        inv.setItem(1, lightblue.build(), null);
        inv.setItem(2, blue.build(), null);
        final ItemBuilder buy = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 13).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kupowanie " + ImportantColor + UnderLined + "przedmiotow" + SpecialSigns + " <<-")));
        inv.setItem(3,buy.build(), (player, inventory, i, itemStack) -> {
            player.closeInventory();
            openBuy(player);
        });
        inv.setItem(4, blue.build(), null);
        final ItemBuilder sell = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Sprzedawanie " + ImportantColor + UnderLined + "przedmiotow" + SpecialSigns + " <<-")));
        inv.setItem(5,sell.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            openSell(player);
        });
        inv.setItem(6, blue.build(), null);
        inv.setItem(7, lightblue.build(), null);
        inv.setItem(8, gold.build(), null);
        inv.openInventory(p);
    }
    public void openBuy(Player p){
            InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kupowanie " + ImportantColor + UnderLined + "CORESV" + SpecialSigns + " <<-")), 4);
            final ItemBuilder magneta = MaterialUtil.getStainedGlassPane((short) 2);
            final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short) 3);
            final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short) 11);
            inv.setItem(0, magneta.build(), null);
            inv.setItem(1, blue.build(), null);
            inv.setItem(2, magneta.build(), null);
            inv.setItem(3, lightblue.build(), null);
            inv.setItem(5, lightblue.build(), null);
            inv.setItem(6, magneta.build(), null);
            inv.setItem(7, blue.build(), null);
            inv.setItem(8, magneta.build(), null);
            inv.setItem(9, blue.build(), null);
            inv.setItem(17, blue.build(), null);
            inv.setItem(18, blue.build(), null);
            inv.setItem(26, blue.build(), null);
            final ItemBuilder back = new ItemBuilder(Material.STAINED_CLAY, 1, (short) 14).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor + "Powrot")));
            inv.setItem(27, back.build(), (player, inventory, i, itemStack) -> {
                player.closeInventory();
                openMain(player);
            });
            inv.setItem(28, lightblue.build(), null);
            inv.setItem(29, blue.build(), null);
            inv.setItem(31, lightblue.build(), null);
            inv.setItem(33, blue.build(), null);
            inv.setItem(34, lightblue.build(), null);
            inv.setItem(35, magneta.build(), null);
            for (Shop shop : Core.getShopStorage().getBuyItems().values()) {
                int index = 0;
                while (inv.get().getItem(index) != null || index == 4) {
                    index++;
                }
                final ItemStack item = shop.getItem();
                final ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kup " + SpecialSigns + "* " + ImportantColor + UnderLined + ItemManager.get(shop.getItem().getType()).toUpperCase())));
                meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Cena: " + ImportantColor + UnderLined + shop.getCoins()))));
                item.setItemMeta(meta);
                inv.setItem(index, item, (player, inventory, i, itemStack) -> {
                    final User u = Core.getUserManager().getUser(player);
                    if (u.getCoins() >= shop.getCoins()) {
                        u.removeCoins(shop.getCoins());
                        ItemUtil.giveItems(Collections.singletonList(shop.getItem()), player);
                        Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Poprawnie zakupiles " + ImportantColor + "przedmiot")));
                        final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
                        inventory.setItem(4, golden.build());
                    } else {
                        Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wystarczajacej ilosci monet!");
                    }
                });
                index++;
            }
            final User u = Core.getUserManager().getUser(p);
            final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                    .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
            inv.setItem(4, golden.build(), null);
            inv.openInventory(p);
    }
    public void openSell(Player p){
        InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Sprzedawanie " + ImportantColor + UnderLined + "CORESV" + SpecialSigns + " <<-")),4);
        final ItemBuilder magneta = MaterialUtil.getStainedGlassPane((short)2);
        final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short)3);
        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short)11);
        inv.setItem(0, magneta.build(),null);
        inv.setItem(1, blue.build(),null);
        inv.setItem(2, magneta.build(),null);
        inv.setItem(3, lightblue.build(),null);
        inv.setItem(5, lightblue.build(),null);
        inv.setItem(6, magneta.build(),null);
        inv.setItem(7, blue.build(),null);
        inv.setItem(8, magneta.build(),null);
        inv.setItem(9, blue.build(),null);
        inv.setItem(17, blue.build(),null);
        inv.setItem(18, blue.build(),null);
        inv.setItem(26, blue.build(),null);
        final ItemBuilder back = new ItemBuilder(Material.STAINED_CLAY,1,(short)14).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor + "Powrot")));
        inv.setItem(27,back.build(),(player, inventory, i, itemStack) -> {
            player.closeInventory();
            openMain(player);
        });
        inv.setItem(28, lightblue.build(),null);
        inv.setItem(29, blue.build(),null);
        inv.setItem(30,lightblue.build(),null);
        final ItemBuilder hopper = new ItemBuilder(Material.HOPPER).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + WarningColor + UnderLined + "SPRZEDAJ WSZYSTKO" + SpecialSigns + " <<-")));
        inv.setItem(31, hopper.build(),(player, inventory, i, itemStack) -> {
            int coins = 0;
            for(Shop shop : Core.getShopStorage().getSellItems().values()){
                final int earn = shop.getCoins()/shop.getItem().getAmount();
                final int amount = ItemStackUtil.getAmountOfItem(shop.getItem().getType(),player,shop.getItem().getDurability());
                if(amount > 0){
                    final ItemStack remove = shop.getItem().clone();
                    remove.setAmount(amount);
                    ItemUtil.removeItems(Collections.singletonList(remove),player);
                    coins += earn*amount;
                }
            }
            if(coins > 0){
                final User u = Core.getUserManager().getUser(player);
                u.addCoins(coins);
                final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                        .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
                inventory.setItem(4,golden.build());
                Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Poprawnie sprzedano wszystkie " + ImportantColor + "przedmioty " + MainColor + "za " + ImportantColor + coins + MainColor + " coinsow")));
            }else{
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie masz czego sprzedac!");
            }
        });
        inv.setItem(32, lightblue.build(),null);
        inv.setItem(33,blue.build(),null);
        inv.setItem(34,lightblue.build(),null);
        inv.setItem(35,magneta.build(),null);
        for(Shop shop : Core.getShopStorage().getSellItems().values()){
            int index = 0;
            while (inv.get().getItem(index) != null || index == 4){
                index ++;
            }
            final ItemBuilder a = new ItemBuilder(shop.getItem().getType(), shop.getItem().getAmount(), shop.getItem().getDurability())
                    .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Sprzedaj " + SpecialSigns + "* " + ImportantColor + UnderLined + ItemManager.get(shop.getItem().getType()).toUpperCase())))
                    .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Cena: " + ImportantColor + UnderLined + shop.getCoins())));
            inv.setItem(index, a.build(), (player, inventory, i, itemStack) -> {
                final User u = Core.getUserManager().getUser(player);
                if(ItemUtil.checkAndRemove(Collections.singletonList(shop.getItem()),player)){
                    u.addCoins(shop.getCoins());
                    Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Poprawnie sprzedales " + ImportantColor + "przedmiot")));
                    final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                            .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
                    inventory.setItem(4,golden.build());
                }else{
                    Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz tego przedmiotu!");
                }
            });
        }
        final User u = Core.getUserManager().getUser(p);
        final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
        inv.setItem(4,golden.build(),null);
        inv.openInventory(p);
    }
    public void openEffect(Player p){
        InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "EFEKTY " + ImportantColor + UnderLined + "CORESV" + SpecialSigns + " <<-")),5);
        final ItemBuilder magneta = MaterialUtil.getStainedGlassPane((short)2);
        final ItemBuilder lightblue = MaterialUtil.getStainedGlassPane((short)3);
        final ItemBuilder blue = MaterialUtil.getStainedGlassPane((short)11);
        inv.setItem(0, blue.build(),null);
        inv.setItem(1,blue.build(),null);
        inv.setItem(2,magneta.build(),null);
        inv.setItem(3,blue.build(),null);
        inv.setItem(5,blue.build(),null);
        inv.setItem(6,magneta.build(),null);
        inv.setItem(7,blue.build(),null);
        inv.setItem(8,blue.build(),null);
        inv.setItem(9,magneta.build(),null);
        inv.setItem(10,blue.build(),null);
        inv.setItem(12,lightblue.build(),null);
        inv.setItem(14,lightblue.build(),null);
        inv.setItem(16,blue.build(),null);
        inv.setItem(17,magneta.build(),null);
        inv.setItem(18,lightblue.build(),null);
        inv.setItem(19,blue.build(),null);
        inv.setItem(21,lightblue.build(),null);
        inv.setItem(23,lightblue.build(),null);
        inv.setItem(25,blue.build(),null);
        inv.setItem(26,lightblue.build(),null);
        inv.setItem(27,lightblue.build(),null);
        inv.setItem(28,lightblue.build(),null);
        inv.setItem(29,lightblue.build(),null);
        inv.setItem(31,magneta.build(),null);
        inv.setItem(33,lightblue.build(),null);
        inv.setItem(34,lightblue.build(),null);
        inv.setItem(35,lightblue.build(),null);
        inv.setItem(36,magneta.build(),null);
        inv.setItem(37,blue.build(),null);
        inv.setItem(38,blue.build(),null);
        inv.setItem(39,magneta.build(),null);
        inv.setItem(40,lightblue.build(),null);
        inv.setItem(41,magneta.build(),null);
        inv.setItem(42,blue.build(),null);
        inv.setItem(43,blue.build(),null);
        inv.setItem(44,magneta.build(),null);
        for(Effect effect : Core.getEffectStorage().getEffects().values()){
            int index = 0;
            while (inv.get().getItem(index) != null || index == 4){
                index ++;
            }
            StringBuilder names = new StringBuilder();
            for(int i = 0 ; i < effect.getPotionEffect().size() ; i++){
                names.append(effect.getPotionEffect().get(i).getType().getName().toUpperCase());
                if(effect.getPotionEffect().size() > i+1){
                    names.append(" | ");
                }
            }
            StringBuilder amplifers = new StringBuilder();
            for(int i = 0 ; i < effect.getPotionEffect().size() ; i++){
                amplifers.append(effect.getPotionEffect().get(i).getAmplifier() + 1);
                if(effect.getPotionEffect().size() > i+1){
                    amplifers.append("/");
                }
            }
            StringBuilder durations = new StringBuilder();
            for(int i = 0 ; i < effect.getPotionEffect().size() ; i++){
                durations.append(effect.getPotionEffect().get(i).getDuration() / 20);
                if(effect.getPotionEffect().size() > i+1){
                    durations.append("/");
                }
            }
            final ItemBuilder a = new ItemBuilder(effect.getGuiMaterial())
                    .setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Kup efekt" + SpecialSigns + " * " + ImportantColor + UnderLined + names + "&2 " + ImportantColor + UnderLined + amplifers)))
                    .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Czas trwania: " + ImportantColor + UnderLined + durations + MainColor + "s")))
                    .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Cena: " + ImportantColor + UnderLined + effect.getCost())));
            inv.setItem(index, a.build(), (player, inventory, i, itemStack) -> {
                final User u = Core.getUserManager().getUser(player);
                if(u.getCoins() >= effect.getCost()){
                    u.removeCoins(effect.getCost());
                    for(PotionEffect effects : effect.getPotionEffect()) {
                        player.addPotionEffect(effects, true);
                    }
                    Util.sendMsg(player, Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Poprawnie zakupiles " + ImportantColor + "efekt")));
                    final ItemBuilder golden = new ItemBuilder(Material.GOLD_NUGGET).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Stan konta" + SpecialSigns + " <<-")))
                            .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Posiadasz: " + ImportantColor + u.getCoins())));
                    inventory.setItem(4,golden.build());
                }else{
                    Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wystarczajacej ilosci monet!");
                }
            });
        }
        inv.openInventory(p);
    }
}
