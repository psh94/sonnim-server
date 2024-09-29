package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;

import java.util.List;

public interface GuesthouseRepositoryCustom {

    List<Guesthouse> findGuesthousesByRegionCode(String regionCode);
    List<Guesthouse> findGuesthousesByWord(String searchWord);
}
