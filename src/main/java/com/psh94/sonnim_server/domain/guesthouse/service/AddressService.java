package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.entity.Address;
import com.psh94.sonnim_server.domain.guesthouse.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    // 지역명으로 지역 코드를 찾아 반환
    public List<String> getRegionCodeFromAddress(String searchWords) {
        List<Address> addressList = addressRepository.findByRegionNameContaining(searchWords); // 검색어가 포함된 주소 정보 조회

        // 주소가 존재하면 각 주소의 지역 코드를 리스트로 수집하여 반환
        return addressList.stream()
                .map(Address::getRegionCode)    // 각 Address 객체에서 regionCode 추출
                .distinct()                     // 중복된 지역 코드 제거 (필요 시)
                .collect(Collectors.toList());  // 리스트로 반환
    }
}