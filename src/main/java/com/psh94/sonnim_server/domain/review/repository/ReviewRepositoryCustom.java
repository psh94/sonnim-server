package com.psh94.sonnim_server.domain.review.repository;

import com.psh94.sonnim_server.domain.review.entity.Review;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findReviewByMemberId(Long memberId);
    List<Review> findReviewByGuesthouseId(Long guesthouseId);
}
