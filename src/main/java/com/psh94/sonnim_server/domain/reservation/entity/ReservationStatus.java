package com.psh94.sonnim_server.domain.reservation.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }
}
