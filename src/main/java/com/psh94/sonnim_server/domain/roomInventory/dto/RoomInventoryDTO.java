package com.psh94.sonnim_server.domain.roomInventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "id")
public class RoomInventoryDTO {

    @NotNull
    private Long id;

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    private int restCapacity;

    @NotNull
    private int price;
}
