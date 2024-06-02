package com.psh94.sonnim_server.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private Long id;
    private String roomName;
    private int maxCapacity;


}
