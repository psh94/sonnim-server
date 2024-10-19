package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;
import com.psh94.sonnim_server.domain.review.dto.ReviewEnrollRequest;
import com.psh94.sonnim_server.domain.review.entity.Review;

public class ReviewConverter {

    // Entity -> DTO 변환
    public static ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .score(review.getScore())
                .title(review.getTitle())
                .content(review.getContent())
                .build();
    }

    // DTO -> Entity 변환
    public static Review toEntity(ReviewDTO reviewDTO) {
        return Review.builder()
                .score(reviewDTO.getScore())
                .title(reviewDTO.getTitle())
                .content(reviewDTO.getContent())
                .build();
    }

    // DTO -> Entity 변환
    public static Review toEntity(ReviewEnrollRequest reviewEnrollRequest, MemberDTO memberDTO, Guesthouse guesthouse) {
        return Review.builder()
                .score(reviewEnrollRequest.getScore())
                .title(reviewEnrollRequest.getTitle())
                .content(reviewEnrollRequest.getContent())
                .member(MemberConverter.toEntity(memberDTO))
                .guesthouse(guesthouse)
                .build();
    }
}