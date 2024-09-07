package pl.blackwater.hardcore.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.TabListType;
import pl.blackwater.hardcore.managers.BanIPManager;
import pl.blackwater.hardcore.managers.BanManager;
import pl.blackwater.hardcore.managers.EventManager;
import pl.blackwater.hardcore.tasks.TabUpdateTask;
import pl.blackwaterapi.utils.PlayerUtil;
import pl.blackwaterapi.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.function.Consumer;

public final class DefaultTablistVariables
{
    private static final Collection<Consumer<TablistVariablesParser>> installers;

    public static void install(final TablistVariablesParser parser) {
        parser.add(new TimeFormattedVariable("HOUR", user -> Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
        parser.add(new TimeFormattedVariable("MINUTE", user -> Calendar.getInstance().get(Calendar.MINUTE)));
        parser.add(new TimeFormattedVariable("SECOND", user -> Calendar.getInstance().get(Calendar.SECOND)));
        parser.add(new SimpleTablistVariable("PLAYER", User::getLastName));
        parser.add(new SimpleTablistVariable("KILLS", user -> String.valueOf(user.getKills())));
        parser.add(new SimpleTablistVariable("DEATHS", user -> String.valueOf(user.getDeaths())));
        parser.add(new SimpleTablistVariable("LOGOUTS", user -> String.valueOf(user.getLogouts())));
        parser.add(new SimpleTablistVariable("KDR", user -> String.valueOf(user.getKd())));
        parser.add(new SimpleTablistVariable("LEVEL", user -> String.valueOf(user.getLevel())));
        parser.add(new SimpleTablistVariable("MONEY", user -> String.valueOf(user.getCoins())));
        parser.add(new GuildDependentTablistVariable("G-TAG", user -> Core.getGuildManager().getGuild(user).getTag(), user -> "BRAK"));
        parser.add(new GuildDependentTablistVariable("G-POINTS", user -> String.valueOf(Core.getGuildManager().getGuild(user).getPoints()), user -> "BRAK"));
        parser.add(new GuildDependentTablistVariable("G-KILLS", user -> String.valueOf(Core.getGuildManager().getGuild(user).getKills()), user -> "BRAK"));
        parser.add(new GuildDependentTablistVariable("G-DEATHS", user -> String.valueOf(Core.getGuildManager().getGuild(user).getDeaths()), user -> "BRAK"));
        parser.add(new GuildDependentTablistVariable("G-ONLINE", user -> "&a" + Core.getMemberManager().getOnlineMembers(Core.getGuildManager().getGuild(user)).size() + "&8/&9" + Core.getMemberManager().getMembers(Core.getGuildManager().getGuild(user)).size(), user -> "BRAK"));
        parser.add(new SimpleTablistVariable("RANK", user -> user.getRank().getName().toUpperCase()));
        parser.add(new SimpleTablistVariable("PING", user -> PlayerUtil.getPing(user.getPlayer()) + "ms"));
        parser.add(new SimpleTablistVariable("TIMEFROMJOIN", User::getOnlineTime));
        parser.add(new SimpleTablistVariable("P-ISTURBODROP", user -> Util.replaceString(user.isTurboDrop() || EventManager.isOnTurboDrop() ? ChatColor.GREEN + "%V%" : ChatColor.RED + "%X%")));
        parser.add(new SimpleTablistVariable("P-ISTURBOEXP", user -> Util.replaceString(user.isTurboExp() || EventManager.isOnTurboExp() ? ChatColor.GREEN + "%V%" : ChatColor.RED + "%X%")));
        parser.add(new SimpleTablistVariable("ISEVENT", user -> Util.replaceString(EventManager.isOnEventDrop() || EventManager.isOnEventExp() ? ChatColor.GREEN + "%V%" : ChatColor.RED + "%X%")));
        parser.add(new SimpleTablistVariable("ONLINE", user -> String.valueOf((int)(Bukkit.getOnlinePlayers().size()))));
        parser.add(new SimpleTablistVariable("REGISTERPLAYERS", user -> String.valueOf(Core.getUserManager().getUsers().size())));
        parser.add(new SimpleTablistVariable("BANSIZE", user -> String.valueOf(BanManager.getBans().size() + BanIPManager.getBansIp().size())));
        parser.add(new SimpleTablistVariable("TPS", user -> Util.replaceString(ChatColor.GREEN + "%V%")));
        parser.add(new SimpleTablistVariable("PVP", user -> Util.replaceString(user.getPlayer().getWorld().getPVP() ? ChatColor.GREEN + "%V%" : ChatColor.RED + "%X%")));
        parser.add(new SimpleTablistVariable("TABLISTTYPE", user -> TabUpdateTask.type.equals(TabListType.KILLS) ? "ZABOJSTW" : TabUpdateTask.type.equals(TabListType.DEATHS) ? "SMIERCI" : "WYKOPANEGO KAMIENIA"));

        for (final Consumer<TablistVariablesParser> installer : DefaultTablistVariables.installers) {
            installer.accept(parser);
        }
    }

    public static void registerInstaller(final Consumer<TablistVariablesParser> parser) {
        DefaultTablistVariables.installers.add(parser);
    }

    static {
        installers = new ArrayList<>();
    }
}
