package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;

public interface GuesthouseService {
    GuesthouseDTO enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest);
    GuesthouseDTO getGuesthouse (Long id);
    void deleteGuesthouse (Long id);
}
