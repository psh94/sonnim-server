package com.psh94.sonnim_server.common.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
    }
    public PasswordMismatchException(String message) {
        super(message);
    }
    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }
}

