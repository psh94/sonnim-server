package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GuesthouseService {
    GuesthouseEnrollResponse enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest);    // 게스트하우스 등록
    GuesthouseDTO getGuesthouse (Long id);                                              // 게스트하우스 단건조회
    Page<GuesthouseDTO> getGuesthousesByRegionCode(String regionCode, int page, int size);               // 게스트하우스 리스트조회(카테고리로 검색 - 지역코드)
    Page<GuesthouseDTO> getGuesthousesByWord(String word, int page, int size);                           // 게스트하우스 리스트조회(직접 검색)
    void deleteGuesthouse (Long id);                                                    // 게스트하우스 삭제
}
