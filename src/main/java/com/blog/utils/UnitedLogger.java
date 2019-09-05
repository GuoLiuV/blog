package com.blog.utils;

import org.apache.log4j.Logger;
public class UnitedLogger {
    public UnitedLogger() {
    }

    public static void debug(Object s) {
        System.err.println(s);
        Logger.getLogger(UnitedLogger.class).debug(s);
    }

    public static void error(Object s) {
        System.err.println(s);
        Logger.getLogger(UnitedLogger.class).error(s);
    }

    public static void info(Object s) {
        System.err.println(s);
    }

    public static void debug(Object s, Object invoder) {
        System.err.println(s);
        Logger.getLogger(UnitedLogger.class).error(s);
    }

    public static void error(Object message, Throwable t) {
        System.err.println(message);
        Logger.getLogger(UnitedLogger.class).error(message, t);
    }

    public static void error(Throwable t) {
        System.err.println(t);
        Logger.getLogger(UnitedLogger.class).error(t.getMessage(), t);
    }
}
