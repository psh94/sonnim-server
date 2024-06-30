package com.psh94.sonnim_server.domain.room.service;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.common.converter.RoomConverter;
import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;
import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final GuesthouseRepository roomGuesthouseRepository;

    @Override
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})
    public RoomDTO enrollRoom(RoomEnrollRequest roomEnrollRequest) {

        Guesthouse guesthouse = roomGuesthouseRepository.findById(roomEnrollRequest.getGuesthouseId())
                .orElseThrow(() -> new GuesthouseNotFoundException("게스트하우스 정보를 찾을 수 없습니다."));

        Room roomEntity = RoomConverter.toEntity(roomEnrollRequest, guesthouse);
        Room savedRoom = roomRepository.save(roomEntity);
        return RoomConverter.toDTO(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDTO getRoomById(Long id) {
        Room foundRoom = roomRepository.findById(id)
                .orElseThrow(()-> new RoomNotFoundException("방 정보를 찾을 수 없습니다."));

        return RoomConverter.toDTO(foundRoom);
    }

    @Override
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})
    public void deleteRoom(Long id) {
        if(!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("방 정보를 찾을 수 없습니다.");
        }
        roomRepository.deleteByIdCustom(id);
    }
}
