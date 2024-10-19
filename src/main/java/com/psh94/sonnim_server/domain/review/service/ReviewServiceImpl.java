package com.psh94.sonnim_server.domain.review.service;

import com.psh94.sonnim_server.common.converter.ReviewConverter;
import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;
import com.psh94.sonnim_server.domain.review.dto.ReviewEnrollRequest;
import com.psh94.sonnim_server.domain.review.entity.Review;
import com.psh94.sonnim_server.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final GuesthouseRepository guesthouseRepository;

    // 리뷰 생성
    @Transactional
    public ReviewDTO createReview(Long guesthouseId, ReviewEnrollRequest reviewEnrollRequest) {
        MemberDTO foundMember = memberService.findMemberByAuth();
        Guesthouse guesthouse = guesthouseRepository.findById(guesthouseId)
                .orElseThrow(() -> new GuesthouseNotFoundException("해당 게스트하우스 정보를 찾을 수 없습니다."));
        Review review = ReviewConverter.toEntity(reviewEnrollRequest, foundMember, guesthouse);
        Review savedReview = reviewRepository.save(review);
        return ReviewConverter.toDTO(savedReview);
    }

    // 단일 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewDTO getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        return ReviewConverter.toDTO(review);
    }

    // 게스트하우스별 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByGuesthouseId(Long guesthouseId) {
        List<Review> reviews = reviewRepository.findByGuesthouseId(guesthouseId);
        return reviews.stream()
                .map(ReviewConverter::toDTO)
                .collect(Collectors.toList());
    }

    // 멤버가 작성한 모든 리뷰 조회
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByMemberId(Long memberId) {
        List<Review> reviews = reviewRepository.findByMemberId(memberId);
        return reviews.stream()
                .map(ReviewConverter::toDTO)
                .collect(Collectors.toList());
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteById(id);
    }

}
