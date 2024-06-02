package com.psh94.sonnim_server.domain.reservation.dto;

import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private int headcount;
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationDTO(Long id, int headcount, ReservationStatus reservationStatus) {
        this.id = id;
        this.headcount = headcount;
        this.reservationStatus = reservationStatus;
    }
}
