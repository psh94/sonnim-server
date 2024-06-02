package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;

public class ReservationConverter {

    public static Reservation toEntity(ReservationRequest reservationRequest){
        return Reservation.builder()
                .headcount(reservationRequest.getHeadcount())
                .reservationStatus(ReservationStatus.PENDING)
                .build();

    }

    public static ReservationDTO toDTO(Reservation reservation){
        return ReservationDTO.builder()
                .id(reservation.getId())
                .headcount(reservation.getHeadcount())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
