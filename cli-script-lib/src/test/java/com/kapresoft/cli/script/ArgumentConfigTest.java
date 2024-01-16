package com.kapresoft.cli.script;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArgumentConfigTest {

    @Test
    void shouldHaveDefaultValues() {
        ArgumentConfig conf = ArgumentConfig.builder()
                .longName("verbose")
                .build();
        assertThat(conf).isNotNull();
        assertThat(conf.getLongName()).as("LongName")
                .isEqualTo("verbose");
        assertThat(conf.getShortName()).as("ShortName")
                .isEqualTo("v");
        assertThat(conf.isFlag()).as("Flag")
                .isFalse();
        assertThat(conf.isRequired()).as("Required")
                .isFalse();
    }

    @Test
    void longAndShortArgumentsDiffer() {
        ArgumentConfig conf = ArgumentConfig.builder()
                .longName("verbose")
                .shortName("z")
                .build();
        assertThat(conf).isNotNull();
        assertThat(conf.getLongName()).as("LongName")
                .isEqualTo("verbose");
        assertThat(conf.getShortName()).as("ShortName")
                .isEqualTo("z");
        assertThat(conf.isFlag()).as("Flag")
                .isFalse();
        assertThat(conf.isRequired()).as("Required")
                .isFalse();
    }

    @Test
    void booleanValues() {
        ArgumentConfig conf = ArgumentConfig.builder()
                .longName("verbose")
                .required(true)
                .flag(true)
                .build();
        assertThat(conf).isNotNull();
        assertThat(conf.getLongName()).isEqualTo("verbose");
        assertThat(conf.getShortName()).isEqualTo("v");
        assertThat(conf.isFlag()).as("Flag")
                .isTrue();
        assertThat(conf.isRequired()).as("Required")
                .isTrue();
    }

}
