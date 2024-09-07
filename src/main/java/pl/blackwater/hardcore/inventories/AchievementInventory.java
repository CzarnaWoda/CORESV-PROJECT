package pl.blackwater.hardcore.inventories;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Achievement;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.AchievementType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.MathUtil;
import pl.blackwaterapi.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AchievementInventory implements Colors {

    @Getter private static FileConfiguration achievementStorage = Core.getAchievementStorage().getConfig();

    public static void openAchievementsInventory(Player p, AchievementType achievementType){
        final InventoryGUI inv2 = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(achievementStorage.getString("gui.inventoryname"))), 3);
        int index = 0;
        for(Achievement achievement : Core.getAchievementStorage().getAchviments().values()){
            if(achievement.getAchievementType().equals(achievementType)){

                final User u = Core.getUserManager().getUser(p);
                final int achievementStat = u.getAchievementStat(achievementType);
                final List<String> lore = new ArrayList<>();
                for(String s : achievement.getGui_itemlore()){
                    lore.add(Util.fixColor(Util.replaceString(s.replace("{AMOUNT}", String.valueOf(achievementStat)).replace("{REACHAMOUNT}", String.valueOf(achievement.getAchievementAmount())).replace("{ISDONE}", u.isAchievementCompleted(achievement.getName()) ? achievementStorage.getString("lore.isdone.true") : achievementStorage.getString("lore.isdone.false")).replace("{COLOR}", (achievementStat >= achievement.getAchievementAmount() ? "&a" : "&c")).replace("{PROGRESS}", String.valueOf(MathUtil.round((float)achievementStat / achievement.getAchievementAmount()*100.0, 1))).replace("{MONEY}", String.valueOf(achievement.getRewardCoins())))));
                }

                final ItemBuilder clay = new ItemBuilder(Material.STAINED_CLAY, 1, (achievementStat >= achievement.getAchievementAmount() && !u.isAchievementCompleted(achievement.getName()) ? (byte)14 : (achievementStat >= achievement.getAchievementAmount() && u.isAchievementCompleted(achievement.getName()) ? (byte)5 : (byte)14))).setTitle(((achievementStat >= achievement.getAchievementAmount() && !u.isAchievementCompleted(achievement.getName())) ? Util.replaceString(SpecialSigns + "->> " + WarningColor + "Osiagniecie nie jest ukonczone!") : (achievementStat >= achievement.getAchievementAmount() && u.isAchievementCompleted(achievement.getName()) ? Util.replaceString(SpecialSigns + "->> " + ImportantColor + "Osiagniecie zostalo juz ukonczone!") : Util.replaceString(SpecialSigns + "->> " + WarningColor + "Osiagniecie nie jest ukonczone!"))));
                final ItemBuilder glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (achievementStat >= achievement.getAchievementAmount() && !u.isAchievementCompleted(achievement.getName()) ? (byte)5 : (achievementStat >= achievement.getAchievementAmount() && u.isAchievementCompleted(achievement.getName()) ? (byte)5 : (byte)14))).setTitle(((achievementStat >= achievement.getAchievementAmount() && !u.isAchievementCompleted(achievement.getName())) ? Util.replaceString(SpecialSigns + "->> " + ImportantColor + "Mozna odebrac nagrode za te osiagniecie!") : (achievementStat >= achievement.getAchievementAmount() && u.isAchievementCompleted(achievement.getName()) ? Util.replaceString(SpecialSigns + "->> " + ImportantColor + "Odebrales juz nagrode za te osiagniecie!") : Util.replaceString(SpecialSigns + "->> " + WarningColor + "Nie mozna jeszcze odebrac nagrody!"))));
                final ItemBuilder itemBuilder = new ItemBuilder(achievement.getGui_item(), 1, (byte) achievement.getGui_itemdata()).setTitle(Util.fixColor(Util.replaceString(achievement.getGui_itemname()))).addLores(lore);

                inv2.setItem(index, clay.build(), null);
                inv2.setItem((index+9), itemBuilder.build(), ((player1, inventory1, i3, itemStack1) -> {
                    if(achievementStat >= achievement.getAchievementAmount() && !u.isAchievementCompleted(achievement.getName())){
                        u.addAchievement(achievement.getName());
                        u.addCoins(achievement.getRewardCoins());
                        for(ItemStack is : achievement.getRewardItems()){
                            player1.getInventory().addItem(is);
                        }
                        player1.closeInventory();
                        openAchievementsInventory(player1,achievementType);
                        Util.sendMsg(Bukkit.getOnlinePlayers(), Util.replaceString(achievementStorage.getString("messages.achievementGet").replace("{PLAYER}", player1.getName()).replace("{ACHIEVEMENT}", achievement.getGui_itemname())));
                    }else if(achievementStat >= achievement.getAchievementAmount() && u.isAchievementCompleted(achievement.getName())){
                        Util.sendMsg(p, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Juz odebrales nagrode za te osiagniecie!"));
                    }else{
                        Util.sendMsg(p, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Nie ukonczyles jeszcze tego osiagniecia!"));
                    }
                }));
                inv2.setItem((index+18), glass.build(), null);
                index++;
            }
        }
        inv2.openInventory(p);
    }

    public static void openInventory(Player p){
        InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(achievementStorage.getString("gui.inventoryname"))), achievementStorage.getInt("gui.rows"));
        int index = 0;
        for(AchievementType achievementType : AchievementType.values()){
            ItemBuilder ib = new ItemBuilder(Material.getMaterial(achievementStorage.getString("gui.materials." + achievementType.name().toLowerCase()))).setTitle(Util.fixColor(Util.replaceString(achievementStorage.getString("gui.itemname." + achievementType.name().toLowerCase()))));
            inventoryGUI.setItem(index, ib.build(), ((player, inventory, i1, itemStack) -> openAchievementsInventory(p, achievementType)));
            index++;
        }
        inventoryGUI.openInventory(p);
    }
}
