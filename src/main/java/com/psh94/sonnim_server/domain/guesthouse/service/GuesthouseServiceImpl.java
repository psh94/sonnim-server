package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.common.converter.GuesthouseConverter;
import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import lombok.RequiredArgsConstructor;
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
    public GuesthouseDTO enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest) {
        Guesthouse guesthouseEntity = GuesthouseConverter.toEntity(guesthouseEnrollRequest);
        Guesthouse savedGusethouse = guesthouseRepository.save(guesthouseEntity);
        return GuesthouseConverter.toDTO(savedGusethouse);
    }

    @Override
    @Transactional(readOnly = true)
    public GuesthouseDTO getGuesthouse(Long id) {
        Guesthouse foundGuesthouse  = guesthouseRepository.findById(id)
                .orElseThrow(()-> new GuesthouseNotFoundException("게스트하우스를 찾을 수 없습니다."));
        return GuesthouseConverter.toDTO(foundGuesthouse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuesthouseDTO> getGuesthousesByRegionCode(String regionCode) {
        List<Guesthouse> guesthouses = guesthouseRepository.findGuesthousesByRegionCodeIn(regionCode);

        return guesthouses.stream()
                .map(GuesthouseConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuesthouseDTO> getGuesthousesByWord(String searchWord) {
        List<String> regionCodes = addressService.getRegionCodeFromAddress(searchWord);

        List<Guesthouse> guesthouses;

        if (regionCodes == null || regionCodes.isEmpty()) {
            guesthouses = guesthouseRepository.findGuesthousesByWord(searchWord);
        } else {
            guesthouses = guesthouseRepository.findGuesthousesByRegionCodeIn(regionCodes.toString());
        }

        return guesthouses.stream()
                .map(GuesthouseConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGuesthouse(Long id) {
        if(!guesthouseRepository.existsById(id)) {
            throw new GuesthouseNotFoundException("게스트하우스를 찾을 수 없습니다.");
        }
        guesthouseRepository.deleteById(id);
    }

}
