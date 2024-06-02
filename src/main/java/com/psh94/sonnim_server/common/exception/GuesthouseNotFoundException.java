package com.psh94.sonnim_server.common.exception;

public class GuesthouseNotFoundException extends RuntimeException {

    public GuesthouseNotFoundException() {
    }
    public GuesthouseNotFoundException(String message) {
        super(message);
    }
    public GuesthouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public GuesthouseNotFoundException(Throwable cause) {
        super(cause);
    }
}

