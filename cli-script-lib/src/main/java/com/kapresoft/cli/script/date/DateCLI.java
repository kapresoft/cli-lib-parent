package com.kapresoft.cli.script.date;

import com.kapresoft.cli.script.CommandLine;
import com.kapresoft.cli.script.CommandLineParser;
import com.kapresoft.cli.script.exception.CommandLineException;
import com.kapresoft.cli.script.util.JavaLogger;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Optional.ofNullable;

public class DateCLI implements CommandLine {

    private static final Logger log = JavaLogger.builder()
            .clazz(DateCLI.class)
            .level(Level.INFO)
            .build().getLog();

    private static final String SCRIPT_NAME = "jdate";
    private static final String DEFAULT_PATTERN = "E MMM DD HH:mm:ss z yyyy";

    public void execute(String[] argsArr) throws CommandLineException {
        log.fine("args[]: %s%n".formatted(Arrays.toString(argsArr)));

        final CommandLineParser parser = CommandLineParser.builder()
                .withArg("help", false, true)
                .withArg("verbose", false, true)
                .withArg("pattern")
                .withArg("date")
                .withArg("unix")
                .build();
        parser.parseArgs(argsArr);
        final Map<String, String> args = parser.getParsedArgs();

        if (args.containsKey("verbose")) {
            System.out.printf("[V]: args=[ %s ]%n", args);
            System.out.printf("Classpath: %s%n", System.getProperty("java.class.path"));
            System.out.printf("User Dir: %s%n", System.getProperty("user.dir"));
        }

        if (args.containsKey("help")) {
            printHelp();
            return;
        }

        String pattern = DEFAULT_PATTERN;
        if (args.containsKey("unix")) {
            System.out.println(Instant.now().getEpochSecond());
            return;
        }

        if (args.containsKey("date")) {
            pattern = "yyyy-MM-dd";
        } else if (args.containsKey("pattern")) {
            pattern = ofNullable(args.get("pattern"))
                    .filter(s -> !s.isBlank())
                    .orElse(DEFAULT_PATTERN);
        }
        ZonedDateTime date = ZonedDateTime.now();
        String output = formatZoneDateTime(date, pattern);
        System.out.println(output);
    }

    private static String formatZoneDateTime(ZonedDateTime date, String pattern) {
        log.fine("Pattern: " + pattern);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    private static void printHelp() {
        System.out.printf("Usage: %s [OPTIONS]%n", SCRIPT_NAME);
        System.out.println("Prints the current date in a specific format.");
        System.out.printf(" %s:%n", "OPTIONS");
        System.out.printf("   %-20s: %s %n", "-d or --date", "Local date yyyy-MM-dd");
        System.out.printf("   %-20s: %s %n", "-u or --unix", "Unix Time");
        System.out.printf("   %-20s: %s %n", "-p or --pattern", "Custom Pattern, ie. yyyy-MM-dd ss.SSS");
        System.out.printf("   %-20s: %s %n", "-h", "print this help");
        System.out.printf("Examples:%n");
        System.out.printf("  %s%n  # Output: %s %n", "jdate", "Mon Jan 15 23:19:16 PST 2024");
        System.out.println();
        System.out.printf("  %s%n  # Output: %s %n", "jdate -p \"YYYY-DD-MMM HH:mm:ss.SSS VV\"", "2024-15-Jan 25.278 America/Los_Angeles");
        System.out.printf("  %n%s:%n  • %s%n", "Available Patterns", "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html");
        System.out.println();
    }

}
