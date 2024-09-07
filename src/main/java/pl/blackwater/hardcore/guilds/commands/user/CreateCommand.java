package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwater.hardcore.utils.StringUtil;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.List;

public class CreateCommand extends PlayerCommand implements Colors {
    public CreateCommand() {
        super("zaloz", "Zaklada nowa gildie na serwerze", "/g zaloz [tag] [nazwa]", "guild.main", "stworz","create");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 2)
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        final String name = args[1];
        final String tag = args[0];

        if(Core.getGuildManager().getGuild(player) != null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Posiadasz juz gildie!");
        }
        //TODO enable manager
        if(tag.length() < 2 || tag.length() > 4 || name.length() < 2 || name.length() > 32){
            return Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Nazwa gildi musi miec od 2 do 32 znakow, tag musi miec od 2 do 4 znakow!");
        }
        if(!StringUtil.isAlphaNumeric1(tag) || !StringUtil.isAlphaNumeric(name))
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Tag oraz nazwa musza byc alfanumeryczne, oraz w tagu nie moga znalezc sie male litery!");
        if(Core.getGuildManager().getGuild(tag) != null || Core.getGuildManager().getGuild(name) != null)
            return Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Gildia o takiej nazwie/tagu juz istnieje!");
        if(!Core.getGuildManager().canCreateGuild(player.getLocation())){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz zalozyc tu gildi, w poblizu znajduje sie spawn lub inna gildia!");
        }
        if(player.hasPermission("guild.admin")){
            Core.getGuildManager().createGuild(tag,name,player);
            Bukkit.broadcastMessage(guildPrefix + "Administrator " + ImportantColor + player.getDisplayName() + MainColor + " zalozyl gildie o nazwie " + SpecialSigns + "[" + ImportantColor + tag + SpecialSigns + "]" + ImportantColor + name);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENDERDRAGON_DEATH, 2.0F, 1.0F);
            }
            return Util.sendMsg(player, WarningColor + "Pamietaj! " + WarningColor_2 + "Na poziomie Y:40 pojawilo sie serce gildi, strzez go");
        }
        final List<ItemStack> guildItems = (player.hasPermission("guild.vip") ? GuildConfig.GUILD_ITEMS_VIP : GuildConfig.GUILD_ITEMS_DEFAULT);
        final User user = Core.getUserManager().getUser(player);
        if((ItemUtil.checkAndRemove(guildItems, player) && !GuildConfig.GUILD_ITEMS_COINS_ENABLED) || (ItemUtil.checkAndRemove(guildItems,player) && user.getCoins() >= GuildConfig.GUILD_ITEMS_COINS_AMOUNT)){
            user.removeCoins(GuildConfig.GUILD_ITEMS_COINS_AMOUNT);
            Core.getGuildManager().createGuild(tag,name,player);
            Bukkit.broadcastMessage(guildPrefix + "Gracz " + ImportantColor + player.getDisplayName() + MainColor + " zalozyl gildie o nazwie " + SpecialSigns + "[" + ImportantColor + tag + SpecialSigns + "]" + ImportantColor + name);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENDERDRAGON_DEATH, 2.0F, 1.0F);
            }
            return Util.sendMsg(player, WarningColor + "Pamietaj! " + WarningColor_2 + "Na poziomie Y:40 pojawilo sie serce gildi, strzez go");
        }else{
            return Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wymaganych przedmiotow do zalozenia gildi, aby sprawdzic czego ci brakuje wpisz /g itemy");
        }
    }
}
