package pl.blackwater.hardcore.guilds.commands.user;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Alliance;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Set;

public class InfoCommand extends PlayerCommand implements Colors {

    public InfoCommand() {
        super("info", "Information about guilds", "/g info <guild>", "guild.main");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length < 1){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        Guild g = Core.getGuildManager().getGuild(args[0]);
        if(g == null){
            return Util.sendMsg(player, MessageConfig.MESSAGE_COMMAND_GETUSAGE.replace("{USAGE}",getUsage()));
        }
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->>->>->>->> [" + MainColor + g.getTag() + SpecialSigns + "] " + WarningColor_2 + "- " + g.getName() + SpecialSigns + " <<-<<-<<-<<-"));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zalozyciel: " + MainColor + g.getGuildLider().getName()));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zastepca: " + MainColor + (g.getGuildPreLider() == null ? "Brak" : g.getGuildPreLider().getName())));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Miejsce: " + MainColor + RankingManager.getPlaceGuild(g)));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Punkty: " + MainColor + g.getPoints()));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zabojstwa: " + MainColor + g.getKills()));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Smierci: " + MainColor + g.getDeaths()));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zycia: " + MainColor + g.getLives()));
        final int size = g.getCuboid().getSize() * 2 +1;
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Teren: " + MainColor + (size*size)));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Utworzona: " + MainColor + Util.getDate(g.getCreateTime())));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Ochrona tnt: " + MainColor + ((g.getCreateTime()+TimeUtil.HOUR.getTime(24)) > System.currentTimeMillis() ? Util.secondsToString((int)(g.getCreateTime()+TimeUtil.HOUR.getTime(24)/1000)) : WarningColor_2 + "Nie")));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Wygasa: " + MainColor + Util.secondsToString((int)g.getExpireTime()/1000)));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Atak: " + MainColor + (System.currentTimeMillis() > g.getLiveCool() ? ImportantColor + "mozliwy" : WarningColor_2 + "mozliwy za " + MainColor + Util.secondsToString((int)g.getLiveCool()/1000))));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Pvp w gildii: " + MainColor + (g.isPvp() ? ImportantColor + "wlaczone" : WarningColor_2 + "wylaczone")));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Czlonkowie gildii: " + MainColor + getMembersToString(g)));
        Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Sojusze: " + MainColor + StringUtils.join(getAlliancesList(g), ", ")));
        return false;
    }

    public static String getMembersToString(Guild guild){
        String[] array = new String[Core.getMemberManager().getMembers(guild).size()];
        int i = 0;
        for(Member m : Core.getMemberManager().getMembers(guild)){
            OfflinePlayer player = Bukkit.getOfflinePlayer(m.getUuid());
            array[i] = (player.isOnline() ? ChatColor.GREEN : ChatColor.DARK_RED) + player.getName();
            i++;
        }
        return StringUtils.join(array, MainColor + ", ");
    }

    public static String[] getAlliancesList(Guild guild){
        final Set<Alliance> alliances = Core.getAllianceManager().getAlliances(guild);
        if(alliances.size() == 0){
            return new String[] {"Brak"};
        }
        String [] s = new String[alliances.size()];
        int i = 0;
        for(Alliance alliance : alliances){
            s[i] = "&6" + (alliance.getGuild_1().equals(guild) ? alliance.getGuild_2().getTag() : alliance.getGuild_1().getTag());
            i++;
        }
        return s;
    }
}
