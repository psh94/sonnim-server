package com.psh94.sonnim_server.domain.guesthouse.service;

import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;

import java.util.List;

public interface GuesthouseService {
    GuesthouseDTO enrollGuesthouse(GuesthouseEnrollRequest guesthouseEnrollRequest);    // 게스트하우스 등록
    GuesthouseDTO getGuesthouse (Long id);                                              // 게스트하우스 단건조회
    List<GuesthouseDTO> getGuesthousesByRegionCode(String regionCode);               // 게스트하우스 리스트조회(카테고리로 검색 - 지역코드)
    List<GuesthouseDTO> getGuesthousesByWord(String word);                           // 게스트하우스 리스트조회(직접 검색)
    void deleteGuesthouse (Long id);                                                    // 게스트하우스 삭제
}
