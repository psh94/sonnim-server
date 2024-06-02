package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryDTO;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryEnrollRequest;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;

public class RoomInventoryConverter {

    public static RoomInventory toEntity(RoomInventoryEnrollRequest roomInventoryEnrollRequest, Room room){
        return RoomInventory.builder()
                .reservationDate(roomInventoryEnrollRequest.getReservationDate())
                .restCapacity(roomInventoryEnrollRequest.getRestCapacity())
                .price(roomInventoryEnrollRequest.getPrice())
                .room(room)
                .build();
    }

    public static RoomInventoryDTO toDTO(RoomInventory roomInventory){
        return RoomInventoryDTO.builder()
                .reservationDate(roomInventory.getReservationDate())
                .restCapacity(roomInventory.getRestCapacity())
                .price(roomInventory.getPrice())
                .build();
    }
}
