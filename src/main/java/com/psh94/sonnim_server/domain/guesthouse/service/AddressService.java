package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.entity.Address;
import com.psh94.sonnim_server.domain.guesthouse.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    // 지역명으로 지역 코드를 찾아 반환
    public String getRegionCodeFromAddress(String searchWords) {
        // 검색어가 포함된 주소 정보 조회
        Address address = addressRepository.findByAddressContaining(searchWords);

        // 주소가 존재하면 해당 지역 코드 반환
        return address != null ? address.getRegionCode() : null;
    }
}