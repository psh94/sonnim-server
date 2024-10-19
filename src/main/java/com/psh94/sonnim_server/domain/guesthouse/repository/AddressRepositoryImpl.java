package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Address;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.entity.QAddress;
import com.psh94.sonnim_server.domain.guesthouse.entity.QGuesthouse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Address> findByRegionNameContaining(String regionName) {

        QAddress address = QAddress.address;

        return queryFactory
                .selectFrom(address)
                .where(address.regionName.containsIgnoreCase(regionName))
                .fetch();
    }
}
