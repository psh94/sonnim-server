package com.psh94.sonnim_server.common.exception;

public class RoomInventoryNotFoundException extends RuntimeException {

    public RoomInventoryNotFoundException() {
    }
    public RoomInventoryNotFoundException(String message) {
        super(message);
    }
    public RoomInventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RoomInventoryNotFoundException(Throwable cause) {
        super(cause);
    }
}

