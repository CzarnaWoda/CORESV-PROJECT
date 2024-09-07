package pl.blackwater.hardcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class ClearCommand extends PlayerCommand implements Colors
{
    public ClearCommand() {
        super("clearinv", "Czyszczenie ekwipunku graczy", "/clearinv [gracz]", "core.clearinv", "clear", "clearinventory", "ci");
    }
    
    public boolean onCommand(Player sender, String[] args) {
        if (args.length != 1) {
        	ClearPlayerInventory(sender);
            return Util.sendMsg(sender, MainColor + "Twoj ekwipunek zostal " + ImportantColor + "wyczyszczony" + MainColor + "!");
        }
        if (!sender.hasPermission("core.clearinv.others")) {
            return Util.sendMsg(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie posiadasz uprawnien do tej komendy " + SpecialSigns + "(" +  WarningColor + "core.clearinv.others" + SpecialSigns + ")");
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            return Util.sendMsg(sender, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
        }
        ClearPlayerInventory(o);
        Util.sendMsg(sender, MainColor + "Wyczyszczono ekwipunek gracza " + ImportantColor + o.getName() + MainColor + "&7!");
        return Util.sendMsg(o, MainColor + "Twoj ekwipunek zostal wyczyszczony przez " + ImportantColor + sender.getName() + MainColor + "!");
    }
    public void ClearPlayerInventory(final Player p)
    {
        final PlayerInventory inv = p.getInventory();
        inv.setArmorContents(new ItemStack[] {null,null,null,null});
        inv.setHeldItemSlot(0);
        inv.clear();
        p.updateInventory();
    }
}
