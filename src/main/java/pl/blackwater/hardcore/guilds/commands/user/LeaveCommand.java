package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.guilds.managers.ScoreBoardManager;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class LeaveCommand extends PlayerCommand implements Colors {
    public LeaveCommand() {
        super("opusc", "Opuszcza gildie", "/g opusc", "guild.main", "leave");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Member member = Core.getMemberManager().getMember(player);
        if(member == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildi");
        }
        if(member.getPosition().equals(Position.LIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Jestes liderem, nie mozesz opuscic gildi");
        }
        for(Guild guild : Core.getGuildManager().getGuilds().values()){
            if(guild.getCuboid().isInCuboid(player.getLocation())){
                return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz opuscic gildi bedac na jej terenie");
            }
        }
        final Guild guild = member.getGuild();
        guild.removeMember(player);
        Bukkit.broadcastMessage(guildPrefix + "Gracz " + ImportantColor + player.getDisplayName() + MainColor + " opuscil gildie " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
        for(Member members : guild.getMembers()){
            Util.sendMsg(members.getPlayer(), WarningColor + "Uwaga, " + WarningColor_2 + "gracz " + player.getDisplayName() + " opuscil gildie!");
        }
        ScoreBoardManager.updateBoard(guild);
        return Util.sendMsg(player, guildPrefix + "Opusciles gildie " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
    }
}
