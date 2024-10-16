package com.psh94.sonnim_server.domain.review.service;

import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO getReview(Long id);
    List<ReviewDTO> getReviewsByGuesthouseId(Long guesthouseId);
    List<ReviewDTO> getReviewsByMemberId(Long memberId);
    void deleteReview(Long id);
}
