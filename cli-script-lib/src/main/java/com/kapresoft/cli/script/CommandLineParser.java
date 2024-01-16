package com.kapresoft.cli.script;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Builder
public class CommandLineParser {

    @Getter
    private final Map<String, String> parsedArgs = new LinkedHashMap<>();
    private final Map<String, ArgumentConfig> argumentConfigMap = new HashMap<>();

    @Singular
    private List<ArgumentConfig> args;

    public void parseArgs(String[] argsArray) {
        // Populate the argument configuration map
        for (ArgumentConfig config : args) {
            argumentConfigMap.put(config.getShortName(), config);
            argumentConfigMap.put(config.getLongName(), config);
        }

        // Iterate through the command-line arguments
        for (int i = 0; i < argsArray.length; i++) {
            String arg = argsArray[i];

            // Check if the argument starts with '-' and has at least one character after the dash
            if (arg.startsWith("-")) {
                String option = arg.startsWith("--") ? arg.substring(2) : arg.substring(1); // Remove the dashes

                // Check if the argument is configured
                if (argumentConfigMap.containsKey(option)) {
                    ArgumentConfig config = argumentConfigMap.get(option);

                    if (config.isFlag()) {
                        // This argument is a flag (no value required)
                        parsedArgs.put(config.getLongName(), null);
                    } else {
                        // Check if there is an optional value for this argument
                        parsedArgs.put(config.getLongName(), null);
                        if (i + 1 < argsArray.length && !argsArray[i + 1].startsWith("-")) {
                            String optionalValue = argsArray[i + 1];
                            parsedArgs.put(config.getLongName(), optionalValue);
                            i++; // Skip the next argument since it's the optional value
                        }
                        if (config.isRequired()) {
                            if (ofNullable(parsedArgs.get(config.getLongName())).isEmpty()) {
                                throw new IllegalArgumentException("Argument '" + option + "' requires a value.");
                            }
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Unknown argument: " + option);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public static class CommandLineParserBuilder {

        public CommandLineParserBuilder withArg(@NonNull String longName, boolean required, boolean flag) {
            return arg(ArgumentConfig.builder()
                    .longName(longName)
                    .required(required)
                    .flag(flag).build());
        }

        public CommandLineParserBuilder withArg(@NonNull String longName, boolean required) {
            return this.withArg(longName, required, false);
        }

        public CommandLineParserBuilder withArg(@NonNull String longName) {
            return this.withArg(longName, false, false);
        }

    }

}
