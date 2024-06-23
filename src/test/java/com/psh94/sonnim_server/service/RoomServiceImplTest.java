package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.room.dto.RoomDTO;
import com.psh94.sonnim_server.domain.room.dto.RoomEnrollRequest;
import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.room.repository.RoomRepository;
import com.psh94.sonnim_server.domain.room.service.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private GuesthouseRepository guesthouseRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private RoomEnrollRequest roomEnrollRequest;
    private Room room;
    private Guesthouse guesthouse;
    private RoomDTO roomDTO;

    @BeforeEach
    void setUp() throws Exception {
        roomEnrollRequest = RoomEnrollRequest.builder()
                .guesthouseId(1L)
                .roomName("1번 방")
                .maxCapacity(10)
                .build();

        guesthouse = Guesthouse.builder()
                .guesthouseName("김씨네 게스트하우스")
                .ownerName("김OO")
                .phone("010-1234-1234")
                .address("jeju")
                .build();


        room = Room.builder()
                .guesthouse(guesthouse)
                .roomName("1번 방")
                .maxCapacity(10)
                .build();

        roomDTO = RoomDTO.builder()
                .id(1L)
                .roomName("1번 방")
                .maxCapacity(10)
                .build();

    }

    @Test
    void 방_등록_성공() {
        when(guesthouseRepository.findById(1L)).thenReturn(Optional.of(guesthouse));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO result = roomService.enrollRoom(roomEnrollRequest);

        assertNotNull(result);
        assertEquals(roomDTO.getRoomName(), result.getRoomName());
        assertEquals(roomDTO.getMaxCapacity(), result.getMaxCapacity());
        verify(guesthouseRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void 방_등록_게스트하우스_조회_실패() {
        when(guesthouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GuesthouseNotFoundException.class, () -> {
            roomService.enrollRoom(roomEnrollRequest);
        });

        verify(guesthouseRepository, times(1)).findById(1L);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void 방_조회_성공() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.getRoomById(1L);

        assertNotNull(result);
        assertEquals(roomDTO.getRoomName(), result.getRoomName());
        assertEquals(roomDTO.getMaxCapacity(), result.getMaxCapacity());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void 방_조회_실패() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> {
            roomService.getRoomById(1L);
        });

        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void 방_삭제_성공() {
        when(roomRepository.existsById(1L)).thenReturn(true);

        roomService.deleteRoom(1L);

        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, times(1)).deleteByIdCustom(1L);
    }

    @Test
    void 방_삭제_조회_실패() {
        when(roomRepository.existsById(1L)).thenReturn(false);

        assertThrows(RoomNotFoundException.class, () -> {
            roomService.deleteRoom(1L);
        });

        verify(roomRepository, times(1)).existsById(1L);
        verify(roomRepository, never()).deleteByIdCustom(anyLong());
    }
}