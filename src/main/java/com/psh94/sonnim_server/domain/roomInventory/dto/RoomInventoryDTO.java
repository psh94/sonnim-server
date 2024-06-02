package com.psh94.sonnim_server.domain.roomInventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInventoryDTO {

    private Long id;
    private LocalDate reservationDate;
    private int restCapacity;
    private int price;
}
