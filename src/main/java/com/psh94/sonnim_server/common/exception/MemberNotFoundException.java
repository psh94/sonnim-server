package com.psh94.sonnim_server.common.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
    }
    public MemberNotFoundException(String message) {
        super(message);
    }
    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MemberNotFoundException(Throwable cause) {
        super(cause);
    }
}

