package com.psh94.sonnim_server.domain.reservation.repository;

import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.reservationStatus = :status WHERE r.id = :id")
    void updateReservationStatus(Long id, ReservationStatus status);
}
