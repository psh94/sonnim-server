package com.psh94.sonnim_server.domain.reservation.service;

import com.psh94.sonnim_server.domain.reservation.dto.ReservationDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;

public interface ReservationService {

    ReservationDTO createReservation(ReservationRequest reservationRequest);
    ReservationDTO getReservationById(Long id);
    void confirmReservation(Long id);
    void cancelReservationById(Long id);
    void deleteReservation(Long id);

}
