package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.EnchantManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;

public class GiveCommand extends Command implements Colors
{
    public GiveCommand() {
        super("give", "dawanie przemiotow graczom", "/give <gracz> <id[:base]> [ilosc] [zaklecia...]", "core.give", "giveitem");
    }
    
    @SuppressWarnings({"rawtypes" })
	public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}", getUsage()));
        }
        Player p = Bukkit.getPlayer(args[0]);
        String[] datas = args[1].split(":");
        Material m = Util.getMaterial(datas[0]);
        short data = ((datas.length > 1) ? Short.parseShort(datas[1]) : 0);
        if (p == null) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
        }
        if (m == null) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nazwa lub ID przedmiotu jest bledne!");
        }
        ItemStack item;
        if (args.length == 2) {
            item = Util.getItemStack(m, data, 1, null);
        }
        else if (args.length == 3) {
            item = Util.getItemStack(m, data, Util.isInteger(args[2]) ? Integer.parseInt(args[2]) : 1, null);
        }
        else {
            HashMap<Enchantment, Integer> enchants = new HashMap<>();
            for (int i = 3; i < args.length; ++i) {
                String[] nameAndLevel = args[i].split(":");
                Enchantment e = EnchantManager.get(nameAndLevel[0]);
                int level = Util.isInteger(nameAndLevel[1]) ? Integer.parseInt(nameAndLevel[1]) : 1;
                enchants.put(e, level);
            }
            item = Util.getItemStack(m, data, Util.isInteger(args[2]) ? Integer.parseInt(args[2]) : 1, enchants);
        }
        Util.giveItems(p, item);
        return Util.sendMsg(sender, MainColor + "Dales " + ImportantColor + UnderLined + m.name().toLowerCase().replace("_", " ") + MainColor + " (" + ImportantColor + "x" + item.getAmount() + MainColor + ") graczowi " + ImportantColor + p.getName() + MainColor + "!");
    }
}
