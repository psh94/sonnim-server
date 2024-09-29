package com.psh94.sonnim_server.domain.room.repository;

import com.psh94.sonnim_server.domain.room.entity.QRoom;
import com.psh94.sonnim_server.domain.room.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findRoomsByGuesthouseId(Long id) {
        QRoom room = QRoom.room;

        return queryFactory
                .selectFrom(room)
                .where(room.guesthouse.id.eq(id))
                .fetch();
    }
}