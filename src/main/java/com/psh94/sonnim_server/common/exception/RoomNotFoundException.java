package com.psh94.sonnim_server.common.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException() {
    }
    public RoomNotFoundException(String message) {
        super(message);
    }
    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RoomNotFoundException(Throwable cause) {
        super(cause);
    }
}

