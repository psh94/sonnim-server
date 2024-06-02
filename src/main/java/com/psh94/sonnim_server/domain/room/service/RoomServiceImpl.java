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

    /*
     * JpaRepositoy의 deleteById은 delete문을 실행하기 전에 해당 객체에 매핑된 다른 객체들을 select문을 수행한다.
     * 만약, 매핑된 다른 객체들이 NULL이라면 select문을 수행하다 에러가 발생할 수 있다.
     * 그래서 delete문만 수행하는 deleteByIdCustom을 만들어 해당 메서드를 수행한다.
     */
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
