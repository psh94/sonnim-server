package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Address;

import java.util.List;

public interface AddressRepositoryCustom {

    List<Address> findByRegionNameContaining(String regionName);
}
