package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;

public class ReservationConverter {

    public static Reservation toEntity(ReservationRequest reservationRequest, MemberDTO member){
        return Reservation.builder()
                .headcount(reservationRequest.getHeadcount())
                .reservationStatus(ReservationStatus.PENDING)
                .member(MemberConverter.toEntity(member))
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
