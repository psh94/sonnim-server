package com.psh94.sonnim_server.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    private List<Long> roomInventoryIds;
    private int headcount;

    @Builder
    public ReservationRequest(List<Long> roomInventoryIds, int headcount) {
        this.roomInventoryIds = roomInventoryIds;
        this.headcount = headcount;
    }
}
