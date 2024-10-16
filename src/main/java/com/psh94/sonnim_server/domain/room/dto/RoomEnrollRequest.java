package com.psh94.sonnim_server.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomEnrollRequest {

    @NotNull
    private Long guesthouseId;

    @NotBlank
    private String roomName;

    @NotNull
    private int maxCapacity;

}
