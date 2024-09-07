package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.managers.ScoreBoardManager;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.Util;

import java.util.List;

public class JoinCommand extends PlayerCommand implements Colors {
    public JoinCommand() {
        super("dolacz", "Przyjmuje zaproszenie do gildi", "/g dolacz <gildia>", "guild.main", "join","przyjmizaproszenie");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 1){
            return Util.sendMsg(player,MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        final Guild guild = Core.getGuildManager().getGuild(args[0]);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Taka gildia nie istnieje");
        }
        if(!guild.getInvites().contains(player.getUniqueId())){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz zaproszenia do tej gildi");
        }
        if(guild.getPlayerLimits() <= guild.getMembers().size()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Gildia nie posiada miejsca na kolejnego czlonka (" + guild.getMembers().size() + "/" + guild.getPlayerLimits() + ")");
        }
        final List<ItemStack> items = ItemUtil.getItems(GuildConfig.GUILD_ITEMS_JOIN, GuildConfig.GUILD_ITEMS_JOINMULTIPLER * guild.getMembers().size());
        if(ItemUtil.checkAndRemove(items,player)){
            guild.addMember(player);
            guild.getInvites().remove(player.getUniqueId());
            Bukkit.broadcastMessage(guildPrefix + "Gracz " + ImportantColor + player.getDisplayName() + MainColor + " dolaczyl do gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
            ScoreBoardManager.updateBoard(guild);
            return Util.sendMsg(player, guildPrefix + "Dolaczyles do gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
        }else{
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wymaganych przedmiotow aby dolaczyc do gildi: " + ItemUtil.getItems(items));
        }
    }
}
