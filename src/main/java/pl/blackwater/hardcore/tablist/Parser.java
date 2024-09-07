package pl.blackwater.hardcore.tablist;

import org.bukkit.ChatColor;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.enums.TabListType;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwater.hardcore.tasks.TabUpdateTask;
import pl.blackwaterapi.utils.Util;

import java.util.List;

public final class Parser implements Colors {


    public static String parsePlace(final String var) {
        if (!var.contains("TOP-")) {
            return null;
        }
        final int index = getIndex(var);
        if(var.contains("GTOP")){
            return Util.fixColor(Util.replaceString(getPlaceGuild(index)));
        }else if(var.contains("PTOP")){
            return Util.fixColor(Util.replaceString(getPlaceUser(index)));
        }
        return null;
    }
    public static int getIndex(final String var) {
        final StringBuilder sb = new StringBuilder();
        boolean open = false;
        boolean start = false;
        int result = -1;
        for (final char c : var.toCharArray()) {
            boolean end = false;
            switch (c) {
                case '{': {
                    open = true;
                    break;
                }
                case '-': {
                    start = true;
                    break;
                }
                case '}': {
                    end = true;
                    break;
                }
                default: {
                    if (open && start) {
                        sb.append(c);
                        break;
                    }
                    break;
                }
            }
            if (end) {
                break;
            }
        }
        try {
            result = Integer.parseInt(sb.toString());
        }
        catch (NumberFormatException e) {
            TabListLogger.parser(var + " contains an invalid number: " + sb.toString());
        }
        return result;
    }
    private static String getPlaceUser(int i){
        final List<User> ranking = (TabUpdateTask.type == TabListType.KILLS ? RankingManager.getRankings() : TabUpdateTask.type == TabListType.DEATHS ? RankingManager.getRankingsByDeaths() : RankingManager.getRankingsByStoneBreak());
        if (ranking.size() >= i)
        {
            String s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + "  ->> " + TAB_TopColor + "");
            if(i < 4){
                s = Util.replaceString(ChatColor.AQUA + "" + i + TAB_SpecialSigns + "  ->> " + ChatColor.AQUA + "");
            }
            if (i > 9) {
                s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + " ->> " + TAB_TopColor + "");
            }
            return Util.replaceString(s + ranking.get(i - 1).getLastName());
        }
        String s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + "  ->> " + TAB_TopColor + "Brak");
        if(i < 4){
            s = Util.replaceString(ChatColor.AQUA + "" + i + TAB_SpecialSigns + "  ->> " + ChatColor.AQUA + "Brak");
        }
        if(i > 9){
            s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + " ->> " + TAB_TopColor + "Brak");
        }
        return s;
    }
    private static String getPlaceGuild(int i){
        if (RankingManager.getGuildRankings().size() >= i)
        {
            Guild guild = RankingManager.getGuildRankings().get(i - 1);
            String s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + "  ->> " + TAB_TopColor + "");
            if(i < 4){
                s = Util.replaceString(ChatColor.AQUA + "" + i + TAB_SpecialSigns + "  ->> " + ChatColor.AQUA + "");
            }
            if (i > 9) {
                s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + "  ->> " + TAB_TopColor + "");
            }
            return Util.fixColor(Util.replaceString(s + guild.getTag() + " " + TAB_SpecialSigns_2 + "[" + Tab_ValueColor_2 + "" + guild.getPoints() + TAB_SpecialSigns_2 + "]"));
        }
        String s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + "  ->> " + TAB_TopColor + "Brak");
        if(i < 4){
            s = Util.replaceString(ChatColor.AQUA + "" + i + TAB_SpecialSigns + "  ->> " + ChatColor.AQUA + "Brak");
        }
        if(i > 9){
            s = Util.replaceString(TAB_MainColor + "" + i + TAB_SpecialSigns + " ->> " + TAB_TopColor + "Brak");
        }
        return s;
    }
}

