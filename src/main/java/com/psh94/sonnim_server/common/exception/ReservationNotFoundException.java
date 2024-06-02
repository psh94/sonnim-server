package com.psh94.sonnim_server.common.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException() {
    }
    public ReservationNotFoundException(String message) {
        super(message);
    }
    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ReservationNotFoundException(Throwable cause) {
        super(cause);
    }
}

