package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.guilds.managers.ScoreBoardManager;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class KickCommand extends PlayerCommand implements Colors {
    public KickCommand() {
        super("wyrzuc", "Wyrzuca gracza z gildi", "/g wyrzuc <gracz>", "guild.main", "kick");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 1){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        final User other = Core.getUserManager().getUser(args[0]);
        if(other == null){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNUSER);
        }
        final Member oMember = Core.getMemberManager().getMember(other);
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(oMember == null || !oMember.getGuild().equals(guild)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Ten gracz nie jest czlonkiem twojej gildi");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPermissions().get("kickPlayersPermission").isStatus()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien zeby wyrzucic gracza z gildi");
        }
        if(oMember.getPosition().equals(Position.LIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz wyrzucic lidera lub samego siebie z gildi");
        }
        Core.getMemberManager().removeMember(oMember);
        Bukkit.broadcastMessage(guildPrefix + "Gracz " + ImportantColor + other.getLastName() + MainColor + " zostal wyrzucony z gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
        Util.sendMsg(player, guildPrefix + MainColor + "Wyrzuciles gracza " + ImportantColor +  other.getLastName() + MainColor + " z gildi");
        final Player o = other.getPlayer();
        ScoreBoardManager.updateBoard(guild);
        if(o != null){
            return Util.sendMsg(o, guildPrefix + "Zostales wyrzucony z gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName());
        }
        return false;
    }
}
