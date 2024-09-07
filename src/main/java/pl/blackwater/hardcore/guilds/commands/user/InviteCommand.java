package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class InviteCommand extends PlayerCommand implements Colors {
    public InviteCommand() {
        super("zapros", "Zaprasza lub cofa zaproszenie gracza do gildi", "/g zapros <gracz>/[wyczysc]", "guild.main", "invite");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 1){
            return Util.sendMsg(player,MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildi");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!guild.getOwner().equals(player.getUniqueId()) ||  member != null && member.getPosition().equals(Position.PRELIDER)){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby zaprosic kogos do gildi");
        }
        if(args[0].equalsIgnoreCase("wyczysc")){
            guild.getInvites().clear();
            return Util.sendMsg(player, guildPrefix + "Wyczysciles " + ImportantColor + "wszystkie " + MainColor + "zaproszenia do gildi");
        }
        final Player other = Bukkit.getPlayer(args[0]);
        if(other == null){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER);
        }
        final Member oMember = Core.getMemberManager().getMember(other);
        if(oMember != null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Ten gracz posiada juz gildie");
        }
        if(!guild.getInvites().contains(other.getUniqueId())) {
            guild.getInvites().add(other.getUniqueId());
            Util.sendMsg(player, guildPrefix + other.getDisplayName() + MainColor + " zostal zaproszony do gildi");
            return Util.sendMsg(other, guildPrefix + "Zostales zaproszony do gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName() + MainColor + " przez " + ImportantColor + player.getDisplayName());
        }else{
            guild.getInvites().remove(other.getUniqueId());
            Util.sendMsg(player, guildPrefix + MainColor + "Zaproszenie dla gracza " + ImportantColor + other.getDisplayName() + MainColor + " zostalo cofniete");
            return Util.sendMsg(other, guildPrefix + "Twoje zaproszenie do gildi " + SpecialSigns + "[" + ImportantColor + guild.getTag() + SpecialSigns + "]" + ImportantColor + guild.getName() + MainColor + " zostalo cofniete przez " + ImportantColor + player.getDisplayName());
        }
    }
}
