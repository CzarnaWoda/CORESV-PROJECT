package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.inventories.MysteryEditInventory;
import pl.blackwater.hardcore.settings.CustomItemConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

public class MysteryCommand extends Command implements Colors {

    public MysteryCommand() {
        super("mystery", "Mystery Boxes", "Poprawne uzycie: /mystery [giveall/gracz/edit] [ilosc]", "core.mystery", new String[0]);
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if(args.length < 1) return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args[0].equalsIgnoreCase("giveall")){
                Integer amount = Integer.parseInt(args[1]);
                if(amount != null){
                    Bukkit.getOnlinePlayers().forEach(pp -> pp.getInventory().addItem(getMysteryBox(amount)));
                    return Util.sendMsg(Bukkit.getOnlinePlayers(), Util.replaceString(SpecialSigns + "->> " + MainColor + "Caly serwer otrzymal " + ImportantColor + "x" + amount + " " + MainColor + "mysteryboxow!"));
                }
            }else if(args[0].equalsIgnoreCase("edit")){
                MysteryEditInventory.openMenu(p);
                return Util.sendMsg(p, Util.replaceString(SpecialSigns + "->> " + MainColor + "Edytujesz mysteryboxa!"));
            }else{
                if(Bukkit.getPlayer(args[0]) != null){
                    Player target = Bukkit.getPlayer(args[0]);
                    Integer amount = Integer.parseInt(args[1]);
                    if(amount != null){
                        target.getInventory().addItem(getMysteryBox(amount));
                        Util.sendMsg(p, "&7Dales graczowi &c" + target.getName() + " &cx" + amount + " &5MysteryBoxow!");
                        return Util.sendMsg(target, Util.replaceString(SpecialSigns + "->> " + MainColor + " otrzymales " + ImportantColor + "x" + amount + ChatColor.DARK_PURPLE + " MysteryBoxow!"));
                    }
                }
            }
        }else{
            if(args[0].equalsIgnoreCase("giveall")){
                Integer amount = Integer.parseInt(args[1]);
                if(amount != null){
                    Bukkit.getOnlinePlayers().forEach(pp -> pp.getInventory().addItem(getMysteryBox(amount)));
                    return Util.sendMsg(Bukkit.getOnlinePlayers(), Util.replaceString(SpecialSigns + "->> " + MainColor + "Caly serwer otrzymal " + ImportantColor + "x" + amount + " " + MainColor + "mysteryboxow!"));
                }
            }else if(args[0].equalsIgnoreCase("edit")){
                return Util.sendMsg(sender, WarningColor + "Ta komende trzeba wykonac z poziomu gry!");
            }else{
                if(Bukkit.getPlayer(args[0]) != null){
                    Player target = Bukkit.getPlayer(args[0]);
                    Integer amount = Integer.parseInt(args[1]);
                    if(amount != null){
                        target.getInventory().addItem(getMysteryBox(amount));
                        Util.sendMsg(sender, "&7Dales graczowi &c" + target.getName() + " &cx" + amount + " &5MysteryBoxow!");
                        return Util.sendMsg(target, Util.replaceString(SpecialSigns + "->> " + MainColor + " otrzymales " + ImportantColor + "x" + amount + ChatColor.DARK_PURPLE + " MysteryBoxow!"));
                    }
                }
            }
        }
        return false;
    }

    private ItemStack getMysteryBox(int amount){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_MYSTERYBOX_ITEM),amount);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_MYSTERYBOX_NAME)));
        CustomItemConfig.ITEMS_MYSTERYBOX_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }
}
