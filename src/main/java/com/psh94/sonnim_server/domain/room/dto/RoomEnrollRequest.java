package com.psh94.sonnim_server.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEnrollRequest {

    private Long guesthouseId;
    private String roomName;
    private int maxCapacity;

}
