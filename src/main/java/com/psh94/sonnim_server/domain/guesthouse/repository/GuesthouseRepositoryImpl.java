package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.entity.QGuesthouse;
import com.psh94.sonnim_server.domain.guesthouse.service.AddressService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class GuesthouseRepositoryImpl implements GuesthouseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Guesthouse> findGuesthousesByRegionCode(String regionCode, Pageable pageable) {
        QGuesthouse guesthouse = QGuesthouse.guesthouse;

        List<Guesthouse> guesthouses = queryFactory
                .selectFrom(guesthouse)
                .where(guesthouse.regionCode.eq(regionCode))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(guesthouse.count())
                .where(guesthouse.regionCode.eq(regionCode))
                .fetchOne();

        return new PageImpl<>(guesthouses, pageable, total);  // 페이지 처리
    }

    @Override
    public Page<Guesthouse> findGuesthousesByWord(String searchWord, Pageable pageable) {
        QGuesthouse guesthouse = QGuesthouse.guesthouse;

        List<Guesthouse> guesthouses = queryFactory
                .selectFrom(guesthouse)
                .where(guesthouse.guesthouseName.containsIgnoreCase(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(guesthouse.count())
                .where(guesthouse.guesthouseName.containsIgnoreCase(searchWord))
                .fetchOne();

        return new PageImpl<>(guesthouses, pageable, total);  // 페이지 처리
    }
}