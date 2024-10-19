package com.psh94.sonnim_server.domain.roomInventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    @NotNull
    private int restCapacity;

    @NotNull
    private int price;

    @NotNull
    private Long roomId;
}
