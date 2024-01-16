package com.kapresoft.cli.script.util;

import lombok.Builder;
import lombok.Value;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.Optional.ofNullable;

@Value
public class JavaLogger {

    String logName;
    Logger log;
    boolean useLongName;
    Level level;

    @Builder
    public JavaLogger(Class<?> clazz, String logName, Logger log, boolean useLongName, Level level) {
        this.useLongName = useLongName;
        this.level = ofNullable(level).orElse(Level.WARNING);
        this.logName = ofNullable(clazz).map(n -> {
            if (useLongName) {
                return clazz.getName();
            }
            return clazz.getSimpleName();
        }).orElseGet(() -> ofNullable(logName)
                .orElseThrow(() -> new IllegalArgumentException("Class or logName is required.")));
        this.log = ofNullable(log).orElse(Logger.getLogger(this.logName));
        formatLogger();
    }

    private void formatLogger() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(this.level); // Set the desired logging level

        // Create a SimpleFormatter with a custom pattern
        // Create a SimpleFormatter with a custom pattern
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            @Override
            public synchronized String format(java.util.logging.LogRecord lr) {
                return "[%s] [%s::%s] %s\n".formatted(
                        lr.getLevel(),
                        lr.getLoggerName(), lr.getSourceMethodName(),
                        lr.getMessage());
            }
        };
        consoleHandler.setFormatter(simpleFormatter);
        this.log.addHandler(consoleHandler);
        this.log.setUseParentHandlers(false);
        this.log.setLevel(this.level);
    }

}
