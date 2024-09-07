package pl.blackwater.hardcore.interfaces;

import net.md_5.bungee.api.ChatColor;
import pl.blackwaterapi.utils.Util;

public interface Colors{

    ChatColor CoreColor = ChatColor.DARK_RED;
    ChatColor SpecialSigns = ChatColor.DARK_GRAY;
    ChatColor BOLD = ChatColor.BOLD;
    ChatColor MainColor = ChatColor.GRAY;
    ChatColor ImportantColor = ChatColor.GREEN;
    ChatColor UnderLined = ChatColor.UNDERLINE;
    ChatColor WarningColor = ChatColor.DARK_RED;
    ChatColor WarningColor_2 = ChatColor.RED;
    ChatColor ScoreBoard_MainColor = ChatColor.GRAY;
    ChatColor ScoreBoard_ValueColor = ChatColor.RED;
    ChatColor TAB_MainColor = ChatColor.WHITE;
    ChatColor TAB_ImportantColor = ChatColor.GREEN;
    ChatColor TAB_TitleColor = ChatColor.AQUA;
    ChatColor TAB_TopColor = ChatColor.DARK_AQUA;
    ChatColor TAB_SpecialSigns = ChatColor.DARK_GRAY;
    ChatColor TAB_SpecialSigns_2 = ChatColor.WHITE;
    ChatColor Tab_ValueColor = ChatColor.BLUE;
    ChatColor Tab_ValueColor_2 = ChatColor.DARK_GRAY;
    String guildPrefix = Util.fixColor(Util.replaceString(ImportantColor + "&nGILDIE" + SpecialSigns + " ->> " + MainColor));
}
