package com.psh94.sonnim_server.common.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
    }
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}

