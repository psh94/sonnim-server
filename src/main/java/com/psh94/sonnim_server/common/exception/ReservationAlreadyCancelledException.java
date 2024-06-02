package com.psh94.sonnim_server.common.exception;

public class ReservationAlreadyCancelledException extends RuntimeException {

    public ReservationAlreadyCancelledException() {
    }
    public ReservationAlreadyCancelledException(String message) {
        super(message);
    }
    public ReservationAlreadyCancelledException(String message, Throwable cause) {
        super(message, cause);
    }
    public ReservationAlreadyCancelledException(Throwable cause) {
        super(cause);
    }
}

