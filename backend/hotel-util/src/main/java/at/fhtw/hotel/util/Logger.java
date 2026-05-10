package at.fhtw.hotel.util;

import org.slf4j.LoggerFactory;

public final class Logger {

    private final org.slf4j.Logger delegate;

    private Logger(Class<?> clazz) {
        this.delegate = LoggerFactory.getLogger(clazz);
    }

    public static Logger get(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void trace(String message, Object... args) {
        delegate.trace(message, args);
    }

    public void debug(String message, Object... args) {
        delegate.debug(message, args);
    }

    public void info(String message, Object... args) {
        delegate.info(message, args);
    }

    public void warn(String message, Object... args) {
        delegate.warn(message, args);
    }

    public void error(String message, Object... args) {
        delegate.error(message, args);
    }

    public void error(String message, Throwable throwable) {
        delegate.error(message, throwable);
    }

}