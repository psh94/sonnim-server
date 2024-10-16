package com.psh94.sonnim_server.domain.roomInventory.service;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.common.converter.RoomInventoryConverter;
import com.psh94.sonnim_server.common.exception.RoomInventoryNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomNotFoundException;
import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.room.repository.RoomRepository;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryDTO;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryEnrollRequest;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import com.psh94.sonnim_server.domain.roomInventory.repository.RoomInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomInventoryServiceImpl implements RoomInventoryService {

    private final RoomInventoryRepository roomInventoryRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomInventoryDTO saveInventory(RoomInventoryEnrollRequest roomInventoryEnrollRequest) {
        Room foundRoom = roomRepository.findById(roomInventoryEnrollRequest.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("방 정보를 찾을 수 없습니다."));

        RoomInventory roomInventoryEntity = RoomInventoryConverter.toEntity(roomInventoryEnrollRequest, foundRoom);
        RoomInventory roomInventory =roomInventoryRepository.save(roomInventoryEntity);

        return RoomInventoryConverter.toDTO(roomInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomInventoryDTO getInventoryById(Long id) {
        RoomInventory roomInventory = roomInventoryRepository.findById(id)
                .orElseThrow(() -> new RoomInventoryNotFoundException("방 인벤토리 정보를 찾을 수 없습니다."));

        return RoomInventoryConverter.toDTO(roomInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomInventoryDTO> getInventoriesByRoom(Room room) {
        List<RoomInventory> roomInventoryList = roomInventoryRepository.findByRoom(room);
        List<RoomInventoryDTO> roomInventoryDTOList = new ArrayList<>();

        for(RoomInventory roomInventory : roomInventoryList) {
            RoomInventoryDTO roomInventoryDTO = RoomInventoryConverter.toDTO(roomInventory);
            roomInventoryDTOList.add(roomInventoryDTO);
        }

        return roomInventoryDTOList;
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        if(!roomInventoryRepository.existsById(id)){
            throw new EntityNotFoundException("");
        }

        roomInventoryRepository.deleteById(id);
    }
}
