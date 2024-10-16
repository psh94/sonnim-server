package com.psh94.sonnim_server.domain.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ReservationRequest {

    @NotNull
    private List<Long> roomInventoryIds;

    @NotNull
    private int headcount;

    @Builder
    public ReservationRequest(List<Long> roomInventoryIds, int headcount) {
        this.roomInventoryIds = roomInventoryIds;
        this.headcount = headcount;
    }
}
