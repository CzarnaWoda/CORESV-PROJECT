package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Alliance;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class PvPCommand extends PlayerCommand implements Colors {
    public PvPCommand() {
        super("pvp", "Wlacza lub wylacza sojuszniczy ogien", "/g pvp <sojusz>", "guild.main", "spvp");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor  + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPermissions().get("togglePvPPermission").isStatus()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien w swojej gildii aby to zrobic");
        }
        if(args.length == 0){
            guild.setPvp(!guild.isPvp());
            for(Member members : Core.getMemberManager().getMembers(guild)){
                Util.sendMsg(members.getPlayer() , guildPrefix + "PvP w twojej gildi zostalo " + (guild.isPvp() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone"));
            }
        }else{
            final Guild oGuild = Core.getGuildManager().getGuild(args[0]);
            if(oGuild == null){
                return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz sojuszu z ta gildia");
            }
            final Alliance alliance = Core.getAllianceManager().getAlliance(guild,oGuild);
            if(alliance == null){
                return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz sojuszu z ta gildia");
            }
            alliance.setPvp(!alliance.isPvp());
            for(Member members : Core.getMemberManager().getMembers(guild)){
                Util.sendMsg(members.getPlayer() , guildPrefix + "PvP w sojuszu pomiedzy twoja gildia a " + oGuild.getTag() + " zostalo " + (guild.isPvp() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone"));
            }
            for(Member members : Core.getMemberManager().getMembers(oGuild)){
                Util.sendMsg(members.getPlayer() , guildPrefix + "PvP w sojuszu pomiedzy twoja gildia a " + guild.getTag() + " zostalo " + (guild.isPvp() ? ChatColor.GREEN + "wlaczone" : ChatColor.DARK_RED + "wylaczone"));
            }
        }
        return false;
    }
}
