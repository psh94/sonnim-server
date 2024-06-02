package com.psh94.sonnim_server.domain.reservationRoomInventory.repository;

import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservationRoomInventory.entity.ReservationRoomInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRoomInventoryRepository extends JpaRepository<ReservationRoomInventory, Long> {
    List<ReservationRoomInventory> findByReservation(Reservation reservation);

}
