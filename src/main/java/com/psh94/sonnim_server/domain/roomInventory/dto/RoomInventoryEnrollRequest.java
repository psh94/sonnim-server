package com.psh94.sonnim_server.domain.roomInventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInventoryEnrollRequest {

    private LocalDate reservationDate;
    private int restCapacity;
    private int price;
    private Long roomId;
}
