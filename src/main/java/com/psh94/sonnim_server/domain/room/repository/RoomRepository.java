package com.psh94.sonnim_server.domain.room.repository;

import com.psh94.sonnim_server.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM rooms WHERE room_id = :roomId", nativeQuery = true)
    void deleteByIdCustom(Long roomId);
}
