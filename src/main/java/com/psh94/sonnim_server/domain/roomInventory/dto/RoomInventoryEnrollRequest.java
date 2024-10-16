package com.psh94.sonnim_server.domain.roomInventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomInventoryEnrollRequest {

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private int restCapacity;

    @NotNull
    private int price;

    @NotNull
    private Long roomId;
}
