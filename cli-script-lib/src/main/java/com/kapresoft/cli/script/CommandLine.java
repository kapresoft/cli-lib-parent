package com.kapresoft.cli.script;

import com.kapresoft.cli.script.exception.CommandLineException;

public interface CommandLine {

    void execute(String[] args) throws CommandLineException;

}
