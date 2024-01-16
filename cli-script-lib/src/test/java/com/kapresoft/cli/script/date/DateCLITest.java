package com.kapresoft.cli.script.date;

import org.junit.jupiter.api.Test;

class DateCLITest {

    @Test
    void date() {
        new DateCLI().execute(new String[]{"-d"});
    }
}
