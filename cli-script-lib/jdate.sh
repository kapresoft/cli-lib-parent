#!/usr/bin/env java --source 17 -Dfile.encoding=UTF-8

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Optional.ofNullable;

/**
 * <div>Examples:</div>
 * <pre>
 *     jdate
 *     jdate -d
 *     jdate --pattern "yyyy-MMM-DD 'HELLO'"
 *     jdate -h
 * </pre>
 */
@SuppressWarnings("unused")
public class CommandLine {

    private final static Logger log = Logger.getLogger("jdate");
    private static final String COMMAND_LINE_CLASS = "com.kapresoft.cli.script.date.DateCLI";
    private static final String SHELL_PROFILE_LIB = Path.of(
            System.getenv("SHELL_PROFILE_HOME"), "bin/lib").toString();
    private static final List<Path> paths = List.of(
            Path.of("target/cli-script-lib-1.0.0.jar")
            //Path.of(SHELL_PROFILE_LIB, "cli-script-lib-1.0.0.jar")
    );

    public static void main(String[] args) {
        log.setLevel(Level.WARNING);
        log.info("jar paths: " + paths);
        log.info("user.dir: " + System.getProperty("user.dir"));
        log.info("java.class.path: " + System.getProperty("java.class.path"));
        invoke(args);
    }

    static void invoke(String[] args) {
        try {
            final List<URL> list = new ArrayList<>();
            for (Path p : paths) {
                list.add(p.toUri().toURL());
            }
            URL[] classpaths = list.toArray(new URL[0]);
            try (URLClassLoader classLoader = new URLClassLoader(classpaths)) {
                Class<?> loadedClass = classLoader.loadClass(COMMAND_LINE_CLASS);
                final Method method = loadedClass.getMethod("execute", String[].class);
                method.invoke(loadedClass.getDeclaredConstructor().newInstance(), (Object) args);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            String msg = ofNullable(e.getCause())
                    .map(Throwable::getMessage)
                    .orElse(e.getMessage());
            throw new IllegalArgumentException("Error invoking runner: " + msg, e);
        }
    }

}
