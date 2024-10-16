package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.converter.ReviewConverter;
import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;
import com.psh94.sonnim_server.domain.review.entity.Review;
import com.psh94.sonnim_server.domain.review.repository.ReviewRepository;
import com.psh94.sonnim_server.domain.review.service.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .content("Good place")
                .score(5)
                .build();

        reviewDTO = ReviewConverter.toDTO(review);
    }

    @Test
    void 리뷰_생성_성공() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNotNull(result);
        assertEquals(reviewDTO.getContent(), result.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void 단일_리뷰_조회_성공() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.getReview(1L);

        assertNotNull(result);
        assertEquals(reviewDTO.getContent(), result.getContent());
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    void 단일_리뷰_조회_실패() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getReview(1L);
        });
    }

    @Test
    void 게스트하우스별_리뷰_조회_성공() {
        when(reviewRepository.findByGuesthouseId(anyLong())).thenReturn(List.of(review));

        List<ReviewDTO> result = reviewService.getReviewsByGuesthouseId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findByGuesthouseId(anyLong());
    }

    @Test
    void 멤버별_리뷰_조회_성공() {
        when(reviewRepository.findByMemberId(anyLong())).thenReturn(List.of(review));

        List<ReviewDTO> result = reviewService.getReviewsByMemberId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository, times(1)).findByMemberId(anyLong());
    }

    @Test
    void 리뷰_삭제_성공() {
        when(reviewRepository.existsById(anyLong())).thenReturn(true);

        reviewService.deleteReview(1L);

        verify(reviewRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void 리뷰_삭제_실패() {
        when(reviewRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.deleteReview(1L);
        });
    }
}
