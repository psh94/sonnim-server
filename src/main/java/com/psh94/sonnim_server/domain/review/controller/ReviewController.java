package com.psh94.sonnim_server.domain.review.controller;

import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;
import com.psh94.sonnim_server.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReview(@PathVariable Long id) {
        ReviewDTO reviewDTO = reviewService.getReview(id);
        return ResponseEntity.ok(reviewDTO);
    }

    // 게스트하우스별 리뷰 조회
    @GetMapping("/guesthouse/{guesthouseId}")
    public ResponseEntity<?> getReviewsByGuesthouseId(@PathVariable Long guesthouseId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByGuesthouseId(guesthouseId);
        return ResponseEntity.ok(reviews);
    }

    // 멤버가 작성한 리뷰 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getReviewsByMemberId(@PathVariable Long memberId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByMemberId(memberId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
