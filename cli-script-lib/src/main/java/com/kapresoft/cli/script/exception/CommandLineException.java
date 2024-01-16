package com.kapresoft.cli.script.exception;

@SuppressWarnings("unused")
public class CommandLineException extends RuntimeException {

    public CommandLineException() {
    }

    public CommandLineException(String message) {
        super(message);
    }

    public CommandLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLineException(Throwable cause) {
        super(cause);
    }

    public CommandLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
