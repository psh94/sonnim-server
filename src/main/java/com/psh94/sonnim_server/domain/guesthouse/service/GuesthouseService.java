package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;

public interface GuesthouseService {
    GuesthouseDTO enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest);    // 게스트하우스 등록
    GuesthouseDTO getGuesthouse (Long id);                                              // 게스트하우스 조회
    void deleteGuesthouse (Long id);                                                    // 게스트하우스 삭제
}
