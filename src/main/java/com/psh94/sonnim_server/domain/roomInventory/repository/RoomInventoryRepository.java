package com.psh94.sonnim_server.domain.roomInventory.repository;

import com.psh94.sonnim_server.domain.room.entity.Room;
import com.psh94.sonnim_server.domain.roomInventory.entity.RoomInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {

    List<RoomInventory> findByRoom(Room room);
    List<RoomInventory> findByReservationDate(LocalDateTime reservationDate);
}
