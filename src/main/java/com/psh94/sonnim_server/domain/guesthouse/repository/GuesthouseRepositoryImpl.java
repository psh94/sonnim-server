package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.entity.QGuesthouse;
import com.psh94.sonnim_server.domain.guesthouse.service.AddressService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GuesthouseRepositoryImpl implements GuesthouseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Guesthouse> findGuesthousesByRegionCodeIn(String regionCodes) {
        QGuesthouse guesthouse = QGuesthouse.guesthouse;

        return queryFactory
                .selectFrom(guesthouse)
                .where(guesthouse.regionCode.in(regionCodes))
                .fetch();
    }

    @Override
    public List<Guesthouse> findGuesthousesByWord(String searchWord) {
        QGuesthouse guesthouse = QGuesthouse.guesthouse;

        return queryFactory
                .selectFrom(guesthouse)
                .where(guesthouse.guesthouseName.containsIgnoreCase(searchWord))
                .fetch();
    }
}