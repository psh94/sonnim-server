package com.psh94.sonnim_server.domain.reservation.repository;

import com.psh94.sonnim_server.domain.reservation.entity.QReservation;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reservation> findReservationsByMemberId(Long id) {
        QReservation reservation = QReservation.reservation;

        return queryFactory
                .selectFrom(reservation)
                .where(reservation.member.id.eq(id))
                .fetch();
    }
}
