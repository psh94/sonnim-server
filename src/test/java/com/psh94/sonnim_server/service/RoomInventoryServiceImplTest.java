package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.exception.RoomInventoryNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomNotFoundException;
import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.room.repository.RoomRepository;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryDTO;
import com.psh94.sonnim_server.domain.roomInventory.dto.RoomInventoryEnrollRequest;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import com.psh94.sonnim_server.domain.roomInventory.repository.RoomInventoryRepository;
import com.psh94.sonnim_server.domain.roomInventory.service.RoomInventoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomInventoryServiceImplTest {

    @Mock
    private RoomInventoryRepository roomInventoryRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomInventoryServiceImpl roomInventoryService;

    private RoomInventoryEnrollRequest roomInventoryEnrollRequest;
    private Room room;
    private RoomInventory roomInventory;
    private RoomInventoryDTO roomInventoryDTO;

    @BeforeEach
    void setUp() {
        roomInventoryEnrollRequest = RoomInventoryEnrollRequest.builder()
                .roomId(1L)
                .reservationDate(LocalDate.now())
                .price(20000)
                .restCapacity(10)
                .build();

        room = Room.builder()
                .roomName("1번 방")
                .maxCapacity(10)
                .build();

        roomInventory = RoomInventory.builder()
                .price(20000)
                .reservationDate(LocalDate.now())
                .room(room)
                .restCapacity(10)
                .build();

        roomInventoryDTO = RoomInventoryDTO.builder()
                .id(1L)
                .reservationDate(LocalDate.now())
                .price(20000)
                .restCapacity(10)
                .build();
    }

    @Test
    void 룸인벤토리_저장_성공() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomInventoryRepository.save(any(RoomInventory.class))).thenReturn(roomInventory);

        RoomInventoryDTO result = roomInventoryService.saveInventory(roomInventoryEnrollRequest);

        assertNotNull(result);
        assertEquals(roomInventoryDTO.getReservationDate(), result.getReservationDate());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomInventoryRepository, times(1)).save(any(RoomInventory.class));
    }

    @Test
    void 룸인벤토리_저장_실패() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> {
            roomInventoryService.saveInventory(roomInventoryEnrollRequest);
        });

        verify(roomRepository, times(1)).findById(1L);
        verify(roomInventoryRepository, never()).save(any(RoomInventory.class));
    }

    @Test
    void 룸인벤토리_id로_조회_성공() {
        when(roomInventoryRepository.findById(1L)).thenReturn(Optional.of(roomInventory));

        RoomInventoryDTO result = roomInventoryService.getInventoryById(1L);

        assertNotNull(result);
        assertEquals(roomInventoryDTO.getReservationDate(), result.getReservationDate());
        assertEquals(roomInventoryDTO.getRestCapacity(), result.getRestCapacity());
        verify(roomInventoryRepository, times(1)).findById(1L);
    }

    @Test
    void 룸인벤토리_id로_조회_실패() {
        when(roomInventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomInventoryNotFoundException.class, () -> {
            roomInventoryService.getInventoryById(1L);
        });

        verify(roomInventoryRepository, times(1)).findById(1L);
    }

    @Test
    void 룸인벤토리_룸으로_조회_성공() {
        when(roomInventoryRepository.findByRoom(room)).thenReturn(Arrays.asList(roomInventory));

        List<RoomInventoryDTO> result = roomInventoryService.getInventoriesByRoom(room);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(roomInventoryDTO.getReservationDate(), result.get(0).getReservationDate());
        verify(roomInventoryRepository, times(1)).findByRoom(room);
    }

    @Test
    void 룸인벤토리_삭제_성공() {
        when(roomInventoryRepository.existsById(1L)).thenReturn(true);

        roomInventoryService.deleteInventory(1L);

        verify(roomInventoryRepository, times(1)).existsById(1L);
        verify(roomInventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void 룸인벤토리_삭제_실패() {
        when(roomInventoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            roomInventoryService.deleteInventory(1L);
        });

        verify(roomInventoryRepository, times(1)).existsById(1L);
        verify(roomInventoryRepository, never()).deleteById(1L);
    }
}
