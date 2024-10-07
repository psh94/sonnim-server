package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.common.converter.GuesthouseConverter;
import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollResponse;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuesthouseServiceImpl implements GuesthouseService{

    private final GuesthouseRepository guesthouseRepository;
    private final AddressService addressService;  // AddressService 주입

    @Override
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})  // GUESTHOUSE, ADMIN 권한을 가진 사용자만 접근 가능
    public GuesthouseEnrollResponse enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest) {
        Guesthouse guesthouseEntity = GuesthouseConverter.toEntity(guesthouseEnrollRequest);
        Guesthouse savedGuesthouse = guesthouseRepository.save(guesthouseEntity);

        return GuesthouseEnrollResponse.builder()
                .id(savedGuesthouse.getId())
                .guesthouseName(savedGuesthouse.getGuesthouseName())
                .ownerName(savedGuesthouse.getOwnerName())
                .build();    }

    @Override
    @Transactional(readOnly = true)
    public GuesthouseDTO getGuesthouse(Long id) {
        Guesthouse foundGuesthouse  = guesthouseRepository.findById(id)
                .orElseThrow(()-> new GuesthouseNotFoundException("ID: " + id + "인 게스트하우스를 찾을 수 없습니다."));
        return GuesthouseConverter.toDTO(foundGuesthouse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuesthouseDTO> getGuesthousesByRegionCode(String regionCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Guesthouse> guesthousesPage = guesthouseRepository.findGuesthousesByRegionCode(regionCode, pageable);

        return guesthousesPage.map(GuesthouseConverter::toDTO);  // Page<Guesthouse> -> Page<GuesthouseDTO> 변환
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuesthouseDTO> getGuesthousesByWord(String searchWord, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Guesthouse> guesthousesPage;

        String regionCode = addressService.getRegionCodeFromAddress(searchWord);
        if (regionCode == null) {
            guesthousesPage = guesthouseRepository.findGuesthousesByWord(searchWord, pageable);
        } else {
            guesthousesPage = guesthouseRepository.findGuesthousesByRegionCode(regionCode, pageable);
        }

        return guesthousesPage.map(GuesthouseConverter::toDTO);  // Page<Guesthouse> -> Page<GuesthouseDTO> 변환
    }

    @Override
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})
    public void deleteGuesthouse(Long id) {
        if(!guesthouseRepository.existsById(id)) {
            throw new GuesthouseNotFoundException("ID: " + id + "인 게스트하우스를 찾을 수 없습니다.");
        }
        guesthouseRepository.deleteById(id);
    }

}
