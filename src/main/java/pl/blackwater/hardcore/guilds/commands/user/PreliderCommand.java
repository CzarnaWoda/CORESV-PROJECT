package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class PreliderCommand extends PlayerCommand implements Colors {

    public PreliderCommand() {
        super("zastepca", "Nadawanie zastepcy", "/g zastepca [gracz]", "guild.main", "vlider", "prelider");
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
        if(!member.getPosition().equals(Position.LIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie jestes liderem gildi");
        }
        if(oMember.getPosition().equals(Position.PRELIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Ten gracz jest juz zastepca twojej gildi!");
        }
        oMember.setPosition(Position.PRELIDER);
        if(Bukkit.getPlayer(args[0]) != null){
            Util.sendMsg(Bukkit.getPlayer(args[0]), guildPrefix + "Zostales mianowany " + ImportantColor + "zastepca lidera " + MainColor + "gildi!");
        }
        return Util.sendMsg(player, guildPrefix + "Mianowales czlonka " + ImportantColor + other.getLastName() + MainColor + " na zastepce gildi");
    }
}
