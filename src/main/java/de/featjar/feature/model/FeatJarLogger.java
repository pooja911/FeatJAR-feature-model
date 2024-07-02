package de.featjar.feature.model;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
/**
 * Utility class for logging messages to a file using Java's built-in logging framework.
 * This class provides methods to log messages at different levels (INFO, WARNING, SEVERE)
 * and initializes a logger with a file handler.
 */

public class FeatJarLogger {

    private static final Logger logger = Logger.getLogger(FeatJarLogger.class.getName());
    private static FileHandler fh = null;

    // Initialize logger with file handler
    static {
        try {
            fh = new FileHandler("logfile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // Log a message at INFO level
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Log a message at WARNING level
    public static void logWarning(String message) {
        logger.warning(message);
    }

    // Log a message at SEVERE level
    public static void logError(String message) {
        logger.severe(message);
    }
}
