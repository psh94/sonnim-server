package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.converter.ReservationConverter;
import com.psh94.sonnim_server.common.exception.ReservationAlreadyCancelledException;
import com.psh94.sonnim_server.common.exception.ReservationNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomInventoryNotFoundException;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Role;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import com.psh94.sonnim_server.domain.reservation.repository.ReservationRepository;
import com.psh94.sonnim_server.domain.reservation.service.ReservationServiceImpl;
import com.psh94.sonnim_server.domain.reservationRoomInventory.entity.ReservationRoomInventory;
import com.psh94.sonnim_server.domain.reservationRoomInventory.repository.ReservationRoomInventoryRepository;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import com.psh94.sonnim_server.domain.roomInventory.repository.RoomInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomInventoryRepository roomInventoryRepository;

    @Mock
    private ReservationRoomInventoryRepository reservationRoomInventoryRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private MemberService memberService;

    private ReservationRequest reservationRequest;
    private Reservation reservation;
    private RoomInventory roomInventory;
    private ReservationRoomInventory reservationRoomInventory;
    private ReservationDTO reservationDTO;
    private MemberDTO memberDTO;

    @BeforeEach
    void setUp() {
        reservationRequest = ReservationRequest.builder()
                .headcount(3)
                .roomInventoryIds(Arrays.asList(1L, 2L))
                .build();

        reservation = Reservation.builder()
                .headcount(3)
                .reservationStatus(ReservationStatus.PENDING)
                .build();

        roomInventory = RoomInventory.builder()
                .reservationDate(LocalDate.now())
                .restCapacity(10)
                .price(100)
                .build();

        reservationRoomInventory = ReservationRoomInventory.builder()
                .reservation(reservation)
                .roomInventory(roomInventory)
                .build();

        reservationDTO = ReservationDTO.builder()
                .id(1L)
                .headcount(3)
                .reservationStatus(ReservationStatus.PENDING)
                .build();

        memberDTO = MemberDTO.builder()
                .id(1L)
                .email("test@test.com")
                .password("test1234")
                .name("kim")
                .phone("010-1234-1234")
                .role(Role.USER)
                .build();
    }

    @Test
    void 예약_생성_성공() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(roomInventoryRepository.findById(anyLong())).thenReturn(Optional.of(roomInventory));
        when(reservationRoomInventoryRepository.save(any(ReservationRoomInventory.class))).thenReturn(reservationRoomInventory);
        when(memberService.findMemberByAuth()).thenReturn(memberDTO);

        ReservationDTO result = reservationService.createReservation(reservationRequest);

        assertNotNull(result);
        verify(roomInventoryRepository, times(2)).save(any(RoomInventory.class));
        verify(reservationRoomInventoryRepository, times(2)).save(any(ReservationRoomInventory.class));
    }

    @Test
    void 예약_생성_룸_인벤토리_조회_실패() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(roomInventoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(memberService.findMemberByAuth()).thenReturn(memberDTO);

        assertThrows(RoomInventoryNotFoundException.class, () -> {
            reservationService.createReservation(reservationRequest);
        });
    }

    @Test
    @Transactional(readOnly = true)
    void 예약_조회_성공() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationDTO result = reservationService.getReservationById(reservationId);

        assertNotNull(result);
    }

    @Test
    @Transactional(readOnly = true)
    void 예약_조회_실패() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.getReservationById(reservationId);
        });
    }

    @Test
    void 회원_예약목록_조회_성공() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        when(reservationRepository.findReservationsByMemberId(1L)).thenReturn(reservations);

        List<ReservationDTO> result = reservationService.getReservationsByMemberId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation.getId(), result.get(0).getId());
        assertEquals(reservation.getHeadcount(), result.get(0).getHeadcount());

        verify(reservationRepository, times(1)).findReservationsByMemberId(1L);
    }

    @Test
    void 회원_예약목록_조회_결과없음() {
        when(reservationRepository.findReservationsByMemberId(1L)).thenReturn(new ArrayList<>());

        List<ReservationDTO> result = reservationService.getReservationsByMemberId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(reservationRepository, times(1)).findReservationsByMemberId(1L);
    }

    @Test
    void 예약_승인_성공() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.confirmReservation(reservationId);

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void 예약_승인_실패() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.confirmReservation(reservationId);
        });
    }

    @Test
    void 예약_취소_성공() {
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRoomInventoryRepository.findByReservation(any(Reservation.class)))
                .thenReturn(Arrays.asList(reservationRoomInventory));

        reservationService.cancelReservationById(reservationId);

        verify(roomInventoryRepository, times(1)).save(any(RoomInventory.class));
        verify(reservationRepository, times(1)).updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
    }

    @Test
    void 예약_취소_실패_이미_취소된_예약() {
        Long reservationId = 1L;
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        assertThrows(ReservationAlreadyCancelledException.class, () -> {
            reservationService.cancelReservationById(reservationId);
        });

        verify(reservationRepository, never()).updateReservationStatus(anyLong(), any(ReservationStatus.class));
    }

    @Test
    void 예약_삭제_성공() {
        Long reservationId = 1L;
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.deleteReservation(reservationId);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void 예약_삭제_실패_취소되지_않은_예약() {
        Long reservationId = 1L;
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        assertThrows(IllegalStateException.class, () -> {
            reservationService.deleteReservation(reservationId);
        });

        verify(reservationRepository, never()).deleteById(anyLong());
    }
}