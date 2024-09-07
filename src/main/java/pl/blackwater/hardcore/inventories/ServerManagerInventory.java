package pl.blackwater.hardcore.inventories;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.drop.managers.DropFile;
import pl.blackwater.hardcore.drop.managers.DropManager;
import pl.blackwater.hardcore.drop.settings.Config;
import pl.blackwater.hardcore.enums.InventoryStyleType;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.managers.SpecialStoneManager;
import pl.blackwater.hardcore.managers.TextCommandManager;
import pl.blackwater.hardcore.settings.BackupConfig;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwater.hardcore.settings.TabListConfig;
import pl.blackwater.hardcore.storage.*;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.configs.ConfigManager;
import pl.blackwaterapi.gui.actions.IAction;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;

public class ServerManagerInventory implements Colors {

    public void openMenu(Player player){
        final InventoryGUI inv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Zarzadzanie serwerem" + SpecialSigns + " <<-")),3);

        final ItemBuilder configs = new ItemBuilder(Material.BOOK).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zarzadzanie " + ImportantColor + "konfiguracjami")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Ilosc configow wykrytych przez BW-API: " + ImportantColor + ConfigManager.getConfigslist().size())));

        final ItemBuilder backups = new ItemBuilder(Material.CHEST).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zarzadzanie " + ImportantColor + "backup'ami")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Ilosc backup'ow: " + ImportantColor + Core.getBackupManager().getBackups().size())));

        final ItemBuilder chat = new ItemBuilder(Material.PAPER).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Zarzadzanie " + ImportantColor + "chat'em")))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Aktualny slowdown: " + ImportantColor + CoreConfig.CHATMANAGER_SLOWMODE)))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Status chatu: " + ImportantColor + (Core.getChatManager().isChat() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))))
                .addLore(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Status chatu &6&lVIP&7: " + ImportantColor + (Core.getChatManager().isVipChat() ? ChatColor.GREEN + "%V%" : ChatColor.DARK_RED + "%X%"))));

        inv.setItem(10, configs.build(), (p, inventory, i, item1) -> {
            final InventoryGUI configinv = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + ImportantColor + UnderLined + "Zarzadzanie konfiguracjami" + SpecialSigns + " <<-")),3);

            int index = 0;
            for(ConfigCreator configcreators : ConfigManager.getConfigs().values()) {
                final ItemBuilder cfg = new ItemBuilder(Material.PAPER).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Konfiguracja " + ImportantColor + UnderLined + configcreators.getConfigName())));
                cfg.addEnchantment(Enchantment.LUCK,index+1);

                configinv.setItem(index, cfg.build(), new ReloadConfigurationAction(configcreators.getConfigName()));

                index++;
            }
            final ItemBuilder cfg = new ItemBuilder(Material.PAPER).setTitle(Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Konfiguracja " + ImportantColor + UnderLined + "drops.yml")));
            configinv.setItem(index, cfg.build(), (player1, inventory1, i1, item) -> {
                DropFile.reloadConfig();
                DropManager.setup();
                Core.setDropInventory(new DropInventory(InventoryStyleType.MCKOX));
                final ItemMeta meta = item.getItemMeta();
                meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Przelodawanie " + ChatColor.GREEN + "%V% " + MainColor + ", " + ImportantColor + Util.getDate(System.currentTimeMillis())))));
                item.setItemMeta(meta);
            });

            configinv.openInventory(p);
        });
        inv.setItem(12,backups.build(),(player1, inventory, i, itemStack) -> {
            player1.closeInventory();
            BackupInventory.openInventory().openInventory(player1);
        });
        inv.setItem(14,chat.build(),(player1, inventory, i, itemStack) -> {
            player1.closeInventory();
            ChatInventory.openMenu(player1);
        });
        inv.openInventory(player);
    }
}
@Data
class ReloadConfigurationAction implements IAction,Colors{

    @NonNull private String configName;


    @Override
    public void execute(Player p, Inventory inv, int i, ItemStack item) {
        final ConfigCreator config = ConfigManager.getConfig(getConfigName());
        if(config == null){
            Logger.warning("Error - config doesnt exists");
            return;
        }
        config.reloadConfig();
        switch (getConfigName()){
            case "ranks.yml":
                Core.setRankStorage(new RankStorage());
                Core.getRankStorage().loadRanks();
                break;
            case "kits.yml":
                Core.setKitsStorage(new KitsStorage());
                Core.getKitsStorage().loadKits();
                break;
            case "message.yml":
                new MessageConfig();
                break;
            case "coreconfig.yml":
                Core.setCoreConfig(new CoreConfig());
                break;
            case "achievements.yml":
                Core.setAchievementStorage(new AchievementStorage());
                break;
            case "textcommands.yml":
                Core.setTextCommandManager(new TextCommandManager());
                Core.setTextCommandsStorage(new TextCommandsStorage());
                Core.getTextCommandsStorage().loadTextCommands();
                break;
            case "backup.yml":
                new BackupConfig();
                break;
            case "specialstone.yml":
                Core.setSpecialStoneStorage(new SpecialStoneStorage());
                Core.setSpecialStoneManager(new SpecialStoneManager());
                break;
            case "mysteryconfig.yml":
                Core.setMysteryStorage(new MysteryStorage());
                Core.getMysteryStorage().loadMysteryBox();
                break;
            case "shop.yml":
                Core.setShopStorage(new ShopStorage());
                Core.setShopInventory(new ShopInventory(InventoryStyleType.CORESV));
                break;
            case "effect.yml":
                Core.setEffectStorage(new EffectStorage());
                break;
            case "guildconfig.yml":
                Core.setGuildConfig(new GuildConfig());
                break;
            case "tablist.yml":
                new TabListConfig();
                break;
            case "dropconfig.yml":
                Core.setDropConfig(new Config());
                break;
            default:
                Logger.warning("CONFIG DOESNT EXISTS ! -> IAction -> CASE DOESNT EXISTS");
        }
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(Collections.singletonList(Util.fixColor(Util.replaceString(SpecialSigns + "  ->> " + MainColor + "Przelodawanie " + ChatColor.GREEN + "%V% " + MainColor + ", " + ImportantColor + Util.getDate(System.currentTimeMillis())))));
        item.setItemMeta(meta);
    }
}
