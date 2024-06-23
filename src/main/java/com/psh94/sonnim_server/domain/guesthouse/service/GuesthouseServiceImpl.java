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

@Service
@RequiredArgsConstructor
public class GuesthouseServiceImpl implements GuesthouseService{

    private final GuesthouseRepository guesthouseRepository;

    @Override
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})
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
    @Transactional
    @CheckRole({"GUESTHOUSE","ADMIN"})
    public void deleteGuesthouse(Long id) {
        if(!guesthouseRepository.existsById(id)) {
            throw new GuesthouseNotFoundException("게스트하우스를 찾을 수 없습니다.");
        }
        guesthouseRepository.deleteById(id);
    }

}
