package com.psh94.sonnim_server.domain.guesthouse.repository;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuesthouseRepository extends JpaRepository<Guesthouse, Long> , GuesthouseRepositoryCustom{
}
