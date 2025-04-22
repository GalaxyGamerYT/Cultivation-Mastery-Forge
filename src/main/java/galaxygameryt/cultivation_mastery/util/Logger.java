package galaxygameryt.cultivation_mastery.util;

import com.mojang.logging.LogUtils;

public class Logger {
    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public static void info(String msg) {
        LOGGER.info(log(msg));
    }

    public static void debug(String msg) {
        LOGGER.debug(log(msg));
    }

    public static void warn(String msg) {
        LOGGER.warn(log(msg));
    }

    public static void error(String msg) {
        LOGGER.error(log(msg));
    }

    private static String log(String msg) {
        return String.format("[Cultivation Mastery] %s", msg);
    }
}
