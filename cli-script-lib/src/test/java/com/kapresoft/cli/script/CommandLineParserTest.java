package com.kapresoft.cli.script;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CommandLineParserTest {

    @Test
    void parseArgs() {
        CommandLineParser parser = CommandLineParser.builder()
                .withArg("verbose", false, true)
                .withArg("name", false)
                .build();
        parser.parseArgs(new String[]{"--verbose", "--name", "John"});
        Map<String, String> args = parser.getParsedArgs();
        assertThat(args).as("Args").isNotNull();
        assertThat(args).hasSize(2);
        assertThat(args).containsOnlyKeys("verbose", "name");
        assertThat(args.get("verbose")).isNull();
        assertThat(args.get("name")).isEqualTo("John");
    }

    @Test
    void parseArgsWithMissingRequired() {
        CommandLineParser parser = CommandLineParser.builder()
                .withArg("verbose", false, true)
                .withArg("name", true)
                .build();
        String[] args = {"--verbose", "--name"};
        assertThatThrownBy(() -> parser.parseArgs(args))
                .hasMessage("Argument 'name' requires a value.");
    }

}

