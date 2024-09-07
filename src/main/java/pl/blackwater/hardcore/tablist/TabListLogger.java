package pl.blackwater.hardcore.tablist;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public final class TabListLogger
{
    private static final Logger bukkitLogger;

    public static void update(final String content) {
        TabListLogger.bukkitLogger.info("[FunnyGuilds][Updater] > " + content);
    }

    public static void parser(final String content) {
        TabListLogger.bukkitLogger.severe("[FunnyGuilds][Parser] #> " + content);
    }

    public static void info(final String content) {
        TabListLogger.bukkitLogger.info("[FunnyGuilds] " + content);
    }

    public static void warning(final String content) {
        TabListLogger.bukkitLogger.warning("[FunnyGuilds] " + content);
    }

    public static void error(final String content) {
        TabListLogger.bukkitLogger.severe("[Server thread/ERROR] #!# " + content);
    }

    public static boolean exception(final Throwable cause) {
        return cause == null || exception(cause.getMessage(), cause.getStackTrace());
    }

    public static boolean exception(final String cause, final StackTraceElement[] ste) {
        error("");
        error("[FunnyGuilds] Severe error:");
        error("");
        error("Server Information:");
        error("  FunnyGuilds: twojstary");
        error("  Bukkit: " + Bukkit.getBukkitVersion());
        error("  Java: " + System.getProperty("java.version"));
        error("  Thread: " + Thread.currentThread());
        error("  Running CraftBukkit: " + Bukkit.getServer().getClass().getName().equals("org.bukkit.craftbukkit.CraftServer"));
        error("");
        if (cause == null || ste == null || ste.length < 1) {
            error("Stack trace: no/empty exception given, dumping current stack trace instead!");
            return true;
        }
        error("Stack trace: ");
        error("Caused by: " + cause);
        for (final StackTraceElement st : ste) {
            error("    at " + st.toString());
        }
        error("");
        error("End of Error.");
        error("");
        return false;
    }

    static {
        bukkitLogger = Bukkit.getLogger();
    }
}

