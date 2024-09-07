package pl.blackwater.hardcore.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

import java.util.List;

public class TopInventory  implements Colors {

    public void openInventory(Player player){
        final InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "TOP 15 " + ImportantColor + "SERWERA" + SpecialSigns + " <<-")),1);

        inv.setItem(0,buildTop(RankingManager.getRankings(),Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "TOP 15" + ImportantColor + " ZABOJSTW" + SpecialSigns + " <<-")),Material.DIAMOND_SWORD,TopInventoryType.KILLS).build(),null);
        inv.setItem(1,buildTop(RankingManager.getRankingsByDeaths(),Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "TOP 15" + ImportantColor + " SMIERCI" + SpecialSigns + " <<-")),Material.BARRIER,TopInventoryType.DEATHS).build(),null);
        inv.openInventory(player);
    }
    private ItemBuilder buildTop(List<User> users, String title, Material material, TopInventoryType type){
        final ItemBuilder itemBuilder = new ItemBuilder(material).setTitle(Util.fixColor(Util.replaceString(title)));
        for(int i = 0; i < 15; i ++){
            final String s = i + 1 < 10 ? (i + 1) + " " : (i + 1) + "";
            if(i >= users.size()) {
                itemBuilder.addLore(Util.fixColor(Util.replaceString(SpecialSigns + s + " ->> " + MainColor + "BRAK" + SpecialSigns + " * " + ImportantColor + "0")));
            }else{
                final User user = users.get(i);
                final int value = (type.equals(TopInventoryType.KILLS) ? user.getKills() : (type.equals(TopInventoryType.DEATHS) ? user.getDeaths() : (type.equals(TopInventoryType.BREAKSTONE) ? user.getDropStones() : (type.equals(TopInventoryType.TIMEPLAY) ? user.getTimePlay() : (type.equals(TopInventoryType.EATEDKOX) ? user.getEatedKox() : (type.equals(TopInventoryType.EATEDREF) ? user.getEatedRef() : (type.equals(TopInventoryType.THROWEDPEARL) ? user.getThrowedPearl() : user.getLevel())))))));
                itemBuilder.addLore(Util.fixColor(Util.replaceString(SpecialSigns + s + " ->> " + MainColor + user.getLastName() + SpecialSigns + " * " + ImportantColor + value)));
            }
        }
        return itemBuilder;
    }
}

enum TopInventoryType{
    KILLS,DEATHS,BREAKSTONE,TIMEPLAY,GUILDS,EATEDKOX,EATEDREF,THROWEDPEARL,LEVEL
}
