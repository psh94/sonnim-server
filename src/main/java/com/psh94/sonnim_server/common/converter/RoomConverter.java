package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;
import com.psh94.sonnim_server.domain.room.entity.Room;

public class RoomConverter {
    public static Room toEntity(RoomEnrollRequest roomEnrollRequest, Guesthouse guesthouse){
        return Room.builder()
                .guesthouse(guesthouse)
                .roomName(roomEnrollRequest.getRoomName())
                .maxCapacity(roomEnrollRequest.getMaxCapacity())
                .build();
    }

    public static RoomDTO toDTO(Room room){
        return RoomDTO.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .maxCapacity(room.getMaxCapacity())
                .build();
    }
}
