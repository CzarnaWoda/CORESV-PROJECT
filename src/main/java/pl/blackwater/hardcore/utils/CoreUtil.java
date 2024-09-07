package pl.blackwater.hardcore.utils;

import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Util;

import java.util.concurrent.TimeUnit;

public class CoreUtil {

    public static void sendConsoleMessage(String message){
        Logger.fixColorSend(Util.replaceString("&4&lProjectHC &8&l->> &7" + message));
    }
    public static String getCzasGry(long millis) {
        if (millis == 0L) {
            return "0";
        }
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        if (days > 0L) {
            millis -= TimeUnit.DAYS.toMillis(days);
        }
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        if (hours > 0L) {
            millis -= TimeUnit.HOURS.toMillis(hours);
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if (minutes > 0L) {
            millis -= TimeUnit.MINUTES.toMillis(minutes);
        }
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        if (seconds > 0L) {
            millis -= TimeUnit.SECONDS.toMillis(seconds);
        }
        StringBuilder sb = new StringBuilder();
        if (days > 0L) {
            sb.append(days);
            long i = days % 10L;
            if (i == 1L) {
                sb.append(" dzien ");
            } else {
                sb.append(" dni ");
            }
        }
        if (hours > 0L) {
            sb.append(hours);
            long i = hours % 10L;
            if (i == 1L) {
                sb.append(" godzina ");
            } else if (i < 5L) {
                sb.append(" godziny ");
            } else {
                sb.append(" godzin ");
            }
        }
        if (minutes > 0L) {
            sb.append(minutes);
            long i = minutes % 10L;
            if (i == 1L) {
                sb.append(" minuta ");
            } else if (i < 5L) {
                sb.append(" minuty ");
            } else {
                sb.append(" minut ");
            }
        }
        if (seconds > 0L) {
            sb.append(seconds);
            long i = seconds % 10L;
            if (i == 1L) {
                sb.append(" sekunda");
            } else if (i < 5L) {
                sb.append(" sekundy");
            } else {
                sb.append(" sekund");
            }
        }
        return sb.toString();
    }
}
