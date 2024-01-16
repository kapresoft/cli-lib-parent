package com.kapresoft.cli.script;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import static java.util.Optional.ofNullable;

@With
@Value
public class ArgumentConfig {

    String longName;
    String shortName;
    boolean required;
    boolean flag;

    @Builder
    public ArgumentConfig(@NonNull String longName,
                          String shortName,
                          boolean required,
                          boolean flag) {
        this.longName = longName;
        this.shortName = ofNullable(shortName).orElse(longName.substring(0, 1));
        this.required = required;
        this.flag = flag;
    }

}
