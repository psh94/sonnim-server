package com.psh94.sonnim_server.domain.room.repository;

import com.psh94.sonnim_server.domain.room.entity.Room;

import java.util.List;

public interface RoomRepositoryCustom {

    List<Room> findRoomsByGuesthouseId(Long id);
}
