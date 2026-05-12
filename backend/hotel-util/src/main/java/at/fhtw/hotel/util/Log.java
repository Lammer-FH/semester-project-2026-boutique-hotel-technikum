package at.fhtw.hotel.util;

import org.slf4j.LoggerFactory;

public final class Log {

    private Log() {
    }

    private static org.slf4j.Logger getLogger() {
        return LoggerFactory.getLogger(
                new Object() {}.getClass().getEnclosingClass()
        );
    }

    public static void trace(String message, Object... args) {
        getLogger().trace(message, args);
    }

    public static void debug(String message, Object... args) {
        getLogger().debug(message, args);
    }

    public static void info(String message, Object... args) {
        getLogger().info(message, args);
    }

    public static void warn(String message, Object... args) {
        getLogger().warn(message, args);
    }

    public static void error(String message, Object... args) {
        getLogger().error(message, args);
    }

    public static void error(String message, Throwable throwable) {
        getLogger().error(message, throwable);
    }

}