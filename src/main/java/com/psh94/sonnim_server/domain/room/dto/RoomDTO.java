package com.psh94.sonnim_server.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "id")
public class RoomDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String roomName;

    @NotNull
    private int maxCapacity;


}
