package com.psh94.sonnim_server.domain.roomInventory.service;

import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryDTO;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryEnrollRequest;

import java.util.List;

public interface RoomInventoryService {

    RoomInventoryDTO saveInventory(RoomInventoryEnrollRequest roomInventoryEnrollRequest);
    RoomInventoryDTO getInventoryById(Long id);
    List<RoomInventoryDTO> getInventoriesByRoom(Room room);
    void deleteInventory(Long id);
}
