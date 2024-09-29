package com.psh94.sonnim_server.domain.room.service;

import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;

import java.util.List;

public interface RoomService {

    RoomDTO enrollRoom(RoomEnrollRequest roomEnrollRequest);
    RoomDTO getRoomById(Long id);
    List<RoomDTO> getRoomsByGuesthouseId(Long id);
    void deleteRoom(Long id);
}
