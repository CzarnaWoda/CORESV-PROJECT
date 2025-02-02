package pl.blackwater.hardcore.tablist;

public final class SafeUtils
{
    private static void reportUnsafe(final Throwable t) {
        TabListLogger.exception(t);
    }

    public static <T> T safeInit(final SafeInitializer<T> initializer) {
        try {
            return initializer.initialize();
        }
        catch (Exception e) {
            reportUnsafe(e);
            return null;
        }
    }

    @FunctionalInterface
    public interface SafeInitializer<T>
    {
        T initialize() throws Exception;
    }
}
