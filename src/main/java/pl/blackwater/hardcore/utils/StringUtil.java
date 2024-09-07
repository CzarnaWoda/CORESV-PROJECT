package pl.blackwater.hardcore.utils;

import org.bukkit.ChatColor;
import pl.blackwaterapi.utils.RandomUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StringUtil {
    public static List<ChatColor> colors = Arrays.asList(ChatColor.AQUA,ChatColor.BLACK,ChatColor.BLUE,ChatColor.DARK_AQUA,ChatColor.DARK_BLUE,ChatColor.DARK_GRAY,ChatColor.DARK_GREEN,ChatColor.DARK_PURPLE,ChatColor.DARK_RED,ChatColor.GOLD,ChatColor.GRAY,ChatColor.GREEN,ChatColor.LIGHT_PURPLE,ChatColor.RED,ChatColor.WHITE,ChatColor.YELLOW);

    public static String getDurationBreakdown(long millis) {
        if (millis == 0L) {
            return "0";
        }
        final long days = TimeUnit.MILLISECONDS.toDays(millis);
        if (days > 0L) {
            millis -= TimeUnit.DAYS.toMillis(days);
        }
        final long hours = TimeUnit.MILLISECONDS.toHours(millis);
        if (hours > 0L) {
            millis -= TimeUnit.HOURS.toMillis(hours);
        }
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if (minutes > 0L) {
            millis -= TimeUnit.MINUTES.toMillis(minutes);
        }
        final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        if (seconds > 0L) {
            millis -= TimeUnit.SECONDS.toMillis(seconds);
        }
        final StringBuilder sb = new StringBuilder();
        if (days > 0L) {
            sb.append(days);
            long i = days % 10L;
            if (i == 1L) {
                sb.append(" dzien ");
            }
            else {
                sb.append(" dni ");
            }
        }
        if (hours > 0L) {
            sb.append(hours);
            long i = hours % 10L;
            if (i == 1L) {
                sb.append(" godzine ");
            }
            else if (i < 4L) {
                sb.append(" godziny ");
            }
            else {
                sb.append(" godzin ");
            }
        }
        if (minutes > 0L) {
            sb.append(minutes);
            long i = minutes % 10L;
            if (i == 1L) {
                sb.append(" minute ");
            }
            else if (i < 4L) {
                sb.append(" minuty ");
            }
            else {
                sb.append(" minut ");
            }
        }
        if (seconds > 0L) {
            sb.append(seconds);
            long i = seconds % 10L;
            if (i == 1L) {
                sb.append(" sekunde");
            }
            else if (i < 5L) {
                sb.append(" sekundy");
            }
            else {
                sb.append(" sekund");
            }
        }
        return sb.toString();
    }
    public static String coloredString(final String string) {
        StringBuilder nju = new StringBuilder("§7");
        for (int i = 0; i < string.length(); i++) {
            final int ii = RandomUtil.getRandInt(0, colors.size()-1);
            nju.append(colors.get(ii)).append(string.charAt(i));
        }
        return nju.toString();
    }
    public static boolean isAlphaNumeric(String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }

    public static boolean isAlphaNumeric1(String s) {
        return s.matches("^[A-Z0-9_]*$");
    }
}
