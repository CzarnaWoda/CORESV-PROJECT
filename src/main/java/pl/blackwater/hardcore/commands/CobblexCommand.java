package pl.blackwater.hardcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.RandomUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;

public class CobblexCommand extends PlayerCommand implements Colors {
    public CobblexCommand() {
        super("cobblex", "Cobblex command", "/cobblex", "core.cobblex", "cx");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(ItemUtil.checkAndRemove(Collections.singletonList(new ItemStack(Material.COBBLESTONE,64*9)),player)){
            final ItemStack cobblex = Core.getCustomItemManager().getSpecialstone();
            cobblex.setAmount(RandomUtil.getRandInt(2,4));
            ItemUtil.giveItems(Collections.singletonList(cobblex),player);

            return Util.sendMsg(player, ChatColor.LIGHT_PURPLE + "COBBLEX " + SpecialSigns + "->> " + MainColor + "Wycraftowales " + ChatColor.YELLOW +  ChatColor.UNDERLINE + cobblex.getAmount() + ImportantColor + " COBBLEX");
        }else {
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz przedmiotow: " + ItemUtil.getItems(Collections.singletonList(new ItemStack(Material.COBBLESTONE, 64 * 9))));
        }
    }
}
