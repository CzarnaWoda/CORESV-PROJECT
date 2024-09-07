package pl.blackwater.hardcore.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.concurrent.TimeUnit;

public class RankingCommand extends PlayerCommand implements Colors {
    public RankingCommand() {
        super("ranking", "Pokazuje informacje na temat rakingu danego gracza", "/ranking [gracz]", "core.ranking", "gracz","graczinfo");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0)
            openRankingMenu(player,Core.getUserManager().getUser(player));
        else{
            final User user = Core.getUserManager().getUser(args[0]);
            if(user == null){
                return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
            }
            openRankingMenu(player, user);
        }
        return false;
    }
    public static void openRankingMenu(Player viever, User check){
        final InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Ranking gracza  " + ImportantColor + check.getLastName() + SpecialSigns + " <<-")), 6);

        final ItemBuilder magneta = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)2).setTitle(Util.fixColor("&7"));
        final ItemBuilder pink = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)6).setTitle(Util.fixColor("&7"));
        final ItemBuilder red = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)14).setTitle(Util.fixColor("&7"));
        final ItemBuilder black = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)15).setTitle(Util.fixColor("&7"));

        inventoryGUI.setItem(0,magneta.build(),null);
        inventoryGUI.setItem(1,pink.build(),null);
        inventoryGUI.setItem(2,black.build(),null);
        inventoryGUI.setItem(3,black.build(),null);
        final ItemStack head = ItemUtil.getPlayerHead(check.getLastName());
        final ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Ranking " + SpecialSigns + "* " + ImportantColor + check.getLastName() +  SpecialSigns + " <<-")));
        head.setItemMeta(meta);
        inventoryGUI.setItem(4,head,null);
        inventoryGUI.setItem(5,black.build(),null);
        inventoryGUI.setItem(6,black.build(),null);
        inventoryGUI.setItem(7,pink.build(),null);
        inventoryGUI.setItem(8,red.build(),null);
        inventoryGUI.setItem(9,pink.build(),null);
        inventoryGUI.setItem(10,magneta.build(),null);
        inventoryGUI.setItem(11,pink.build(),null);
        final ItemBuilder pvp = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Statystyki PvP" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Zabojstwa: " + ImportantColor + check.getKills())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Smierci: " + ImportantColor + check.getDeaths())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Logouty: " + ImportantColor + check.getLogouts())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "KD/R: " + ImportantColor + check.getKd())));
        inventoryGUI.setItem(12, pvp.build(),null);
        inventoryGUI.setItem(13,black.build(),null);
        final ItemBuilder drop = new ItemBuilder(Material.DIAMOND_PICKAXE).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Statystyki Kopania" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Wykopany kamien: " + ImportantColor + check.getDropStones())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Wykopane diamenty: " + ImportantColor + check.getDropDiamonds())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Wykopane zloto: " + ImportantColor + check.getDropDiamonds())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Level kopania: " + ImportantColor + check.getLevel())));
        inventoryGUI.setItem(14, drop.build(),null);
        inventoryGUI.setItem(15,pink.build(),null);
        inventoryGUI.setItem(16,red.build(),null);
        inventoryGUI.setItem(17,pink.build(),null);
        inventoryGUI.setItem(18,black.build(),null);
        inventoryGUI.setItem(19,pink.build(),null);
        inventoryGUI.setItem(20,magneta.build(),null);
        inventoryGUI.setItem(21,pink.build(),null);
        //TODO GuildInfo 22 end portal
        inventoryGUI.setItem(23,black.build(),null);
        inventoryGUI.setItem(24,pink.build(),null);
        inventoryGUI.setItem(25,pink.build(),null);
        inventoryGUI.setItem(26,black.build(),null);
        inventoryGUI.setItem(27,black.build(),null);
        inventoryGUI.setItem(28,pink.build(),null);
        inventoryGUI.setItem(29,pink.build(),null);
        inventoryGUI.setItem(30,black.build(),null);
        inventoryGUI.setItem(31,black.build(),null);
        inventoryGUI.setItem(32,pink.build(),null);
        inventoryGUI.setItem(33,magneta.build(),null);
        inventoryGUI.setItem(34,pink.build(),null);
        inventoryGUI.setItem(35,black.build(),null);
        inventoryGUI.setItem(36,pink.build(),null);
        inventoryGUI.setItem(37,red.build(),null);
        inventoryGUI.setItem(38,pink.build(),null);
        inventoryGUI.setItem(39,black.build(),null);
        final ItemBuilder other = new ItemBuilder(Material.BOOK).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Statystyki Ogolne" + SpecialSigns + " <<-")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Spedzony czas: " + ImportantColor + CoreUtil.getCzasGry(TimeUnit.SECONDS.toMillis(check.getTimePlay())))))//TODO add method to calc timeplay to seconds itp, rzucone perly, koxy zjedzone i refile
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Zjedzone koxy: " + ImportantColor + check.getEatedKox())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Zjedzone refile: " + ImportantColor + check.getEatedRef())))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Rzucone perly: " + ImportantColor + check.getThrowedPearl())));
        inventoryGUI.setItem(40, other.build(), null);
        inventoryGUI.setItem(41,black.build(),null);
        inventoryGUI.setItem(42,pink.build(),null);
        inventoryGUI.setItem(43,magneta.build(),null);
        inventoryGUI.setItem(44,pink.build(),null);
        inventoryGUI.setItem(45,red.build(),null);
        inventoryGUI.setItem(46,pink.build(),null);
        inventoryGUI.setItem(47,black.build(),null);
        inventoryGUI.setItem(48,black.build(),null);
        inventoryGUI.setItem(49,black.build(),null);
        inventoryGUI.setItem(50,black.build(),null);
        inventoryGUI.setItem(51,black.build(),null);
        inventoryGUI.setItem(52,pink.build(),null);
        inventoryGUI.setItem(53,magneta.build(),null);
        inventoryGUI.openInventory(viever);
    }
}
