package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class SetHomeCommand extends PlayerCommand implements Colors {
    public SetHomeCommand() {
        super("ustawbaze", "Ustawia nowa lokacje na baze gildii", "/g ustawbaze", "guild.main", "sethome");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor  + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPosition().equals(Position.LIDER) && !member.getPosition().equals(Position.PRELIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien w swojej gildii aby to zrobic");
        }
        if(!guild.getCuboid().isInCuboid(player)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz ustawic bazy poza terenem gildii");
        }
        guild.setHomeLocation(player.getLocation());
        for(Member members : guild.getMembers()){
            Util.sendMsg(members.getPlayer(), guildPrefix + ImportantColor + player.getDisplayName() + MainColor + " ustawil nowa lokacje " + ImportantColor + "bazy gildii");
        }
        return Util.sendMsg(player, guildPrefix + "Ustawiono nowa lokacje " + ImportantColor + "bazy gildii");
    }
}
