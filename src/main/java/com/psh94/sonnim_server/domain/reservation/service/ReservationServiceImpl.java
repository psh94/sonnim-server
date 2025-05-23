package com.psh94.sonnim_server.domain.reservation.service;

import com.psh94.sonnim_server.common.converter.MemberConverter;
import com.psh94.sonnim_server.common.converter.ReservationConverter;
import com.psh94.sonnim_server.common.exception.ReservationAlreadyCancelledException;
import com.psh94.sonnim_server.common.exception.ReservationNotFoundException;
import com.psh94.sonnim_server.common.exception.RoomInventoryNotFoundException;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import com.psh94.sonnim_server.domain.reservation.repository.ReservationRepository;
import com.psh94.sonnim_server.domain.reservationRoomInventory.entity.ReservationRoomInventory;
import com.psh94.sonnim_server.domain.reservationRoomInventory.repository.ReservationRoomInventoryRepository;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import com.psh94.sonnim_server.domain.roomInventory.repository.RoomInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final RoomInventoryRepository roomInventoryRepository;
    private final ReservationRoomInventoryRepository reservationRoomInventoryRepository;
    private final MemberService memberService;


    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationRequest reservationRequest) {
        MemberDTO foundMember = memberService.findMemberByAuth();
        Reservation reservationEntity = ReservationConverter.toEntity(reservationRequest, foundMember);
        Reservation savedReservation = reservationRepository.save(reservationEntity);

        // reservationRequest에는 roomInventoryId가 여러개 들어올 수 있음 그러므로 반복문을 통해 하나씩 처리
        for (Long roomInventoryId : reservationRequest.getRoomInventoryIds()) {
            RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId)
                    .orElseThrow(() -> new RoomInventoryNotFoundException("RoomInventory not found"));

            // 동시성 문제를 예방하기 위해 체크 후 감소 및 저장
            synchronized (roomInventory) {
                if (roomInventory.getRestCapacity() < reservationRequest.getHeadcount()) {
                    throw new IllegalStateException("방에 남은 자리가 부족합니다.");
                }
                roomInventory.reduceCapacity(reservationRequest.getHeadcount());
            }
            roomInventoryRepository.save(roomInventory);

            ReservationRoomInventory reservationRoomInventory = ReservationRoomInventory.builder()
                    .reservation(savedReservation)
                    .roomInventory(roomInventory)
                    .build();

            reservationRoomInventoryRepository.save(reservationRoomInventory);
        }

        return ReservationConverter.toDTO(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다."));

        return ReservationConverter.toDTO(foundReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByMemberId(Long memberId) {

        List<Reservation> reservations = reservationRepository.findReservationsByMemberId(memberId);

        return reservations.stream()
                .map(ReservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void confirmReservation(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다."));

        foundReservation.setReservationStatus(ReservationStatus.valueOf("CONFIRMED"));
        reservationRepository.save(foundReservation);
    }

    @Override
    @Transactional
    public void cancelReservationById(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다."));

        if(foundReservation.getReservationStatus().equals(ReservationStatus.CANCELLED)){
            throw new ReservationAlreadyCancelledException("예약상태가 이미 CANCELLED 입니다.");
        }

        List<ReservationRoomInventory> reservationRoomInventories = reservationRoomInventoryRepository.findByReservation(foundReservation);
        for (ReservationRoomInventory reservationRoomInventory : reservationRoomInventories) {
            RoomInventory roomInventory = reservationRoomInventory.getRoomInventory();
            roomInventory.addCapacity(foundReservation.getHeadcount());
            roomInventoryRepository.save(roomInventory);
        }

        foundReservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.updateReservationStatus(id, ReservationStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다."));

        if(!foundReservation.getReservationStatus().equals(ReservationStatus.CANCELLED)){
            throw new IllegalStateException("취소상태의 예약만 삭제할 수 있습니다.");
        }

        reservationRepository.deleteById(id);
    }
}
