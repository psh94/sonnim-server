package com.psh94.sonnim_server.domain.reservation.dto;

import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "id")
public class ReservationDTO {

    @NotNull
    private Long id;

    @NotNull
    private int headcount;

    @NotNull
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationDTO(Long id, int headcount, ReservationStatus reservationStatus) {
        this.id = id;
        this.headcount = headcount;
        this.reservationStatus = reservationStatus;
    }
}
