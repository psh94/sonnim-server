package com.psh94.sonnim_server.domain.reservation.service;

import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;

import java.util.List;

public interface ReservationService {

    ReservationDTO createReservation(ReservationRequest reservationRequest);
    ReservationDTO getReservationById(Long id);
    List<ReservationDTO> getReservationsByMemberId(Long memberId);
    void confirmReservation(Long id);
    void cancelReservationById(Long id);
    void deleteReservation(Long id);

}
