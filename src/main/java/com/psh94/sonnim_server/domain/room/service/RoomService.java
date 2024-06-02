package com.psh94.sonnim_server.domain.room.service;

import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;

public interface RoomService {

    RoomDTO enrollRoom(RoomEnrollRequest roomEnrollRequest);
    RoomDTO getRoomById(Long id);
    void deleteRoom(Long id);
}
