package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuesthouseRepositoryCustom {

    Page<Guesthouse> findGuesthousesByRegionCode(String regionCode, Pageable pageable);
    Page<Guesthouse> findGuesthousesByWord(String searchWord, Pageable pageable);
}
