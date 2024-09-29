package com.psh94.sonnim_server.domain.reservation.repository;

import com.psh94.sonnim_server.domain.reservation.entity.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findReservationsByMemberId(Long id);
}
