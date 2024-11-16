package dev.jsinco.discord.framework.logging;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger for the framework
 * @since 1.0
 * @author Jonah
 */
@Getter
@Setter
public class FrameWorkLogger {

    @Getter private static final Logger logger = LoggerFactory.getLogger(FrameWorkLogger.class);
    private static FrameWorkLogger instance;

    public static FrameWorkLogger getInstance() {
        if (instance == null) {
            instance = new FrameWorkLogger();
        }
        return instance;
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }


    private ConsoleAppender consoleAppender;

    private FrameWorkLogger() {
        PatternLayout localLayout = PatternLayout.newBuilder()
                .withPattern("[%d{HH:mm:ss} %c{1}/%thread]: %msg%n")
                .build();

        this.consoleAppender = ConsoleAppender.createDefaultAppenderForLayout(localLayout);
    }

    private FrameWorkLogger(ConsoleAppender consoleAppender) {
        this.consoleAppender = consoleAppender;
    }



    public void configureLogging() {
        // Get the current Log4j2 configuration
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        // Add the appender to the configuration
        config.addAppender(consoleAppender);
        consoleAppender.start();
        // Remove all existing appenders from the root logger
        config.getRootLogger().getAppenders().values().forEach(appender -> config.getRootLogger().removeAppender(appender.getName()));
        // Add the appender to the root logger with null filter and level
        config.getRootLogger().setAdditive(false);
        config.getRootLogger().setLevel(Level.INFO);
        config.getRootLogger().addAppender(consoleAppender, Level.INFO, null);



        // Update the existing configuration with the modifications
        context.updateLoggers();
    }
}
