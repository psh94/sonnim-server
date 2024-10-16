package com.psh94.sonnim_server.domain.review.repository;

import com.psh94.sonnim_server.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGuesthouseId(Long guesthouseId); // 게스트하우스별 리뷰 조회
    List<Review> findByMemberId(Long memberId);         // 멤버가 작성한 리뷰 조회
}
