package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.converter.ReviewConverter;
import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Role;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import com.psh94.sonnim_server.domain.review.dto.ReviewDTO;
import com.psh94.sonnim_server.domain.review.dto.ReviewEnrollRequest;
import com.psh94.sonnim_server.domain.review.entity.Review;
import com.psh94.sonnim_server.domain.review.repository.ReviewRepository;
import com.psh94.sonnim_server.domain.review.service.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private GuesthouseRepository guesthouseRepository;

    private Review review;
    private ReviewDTO reviewDTO;
    private ReviewEnrollRequest reviewEnrollRequest;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .content("Good place")
                .score(5)
                .build();

        reviewEnrollRequest = ReviewEnrollRequest.builder()
                .title("잘놀고가요~")
                .content("좋아요~")
                .score(5)
                .build();

        reviewDTO = ReviewConverter.toDTO(review);
    }

    @Test
    void 리뷰_생성_성공() throws IllegalAccessException, NoSuchFieldException {
        Long guesthouseId = 1L;

        MemberDTO memberDTO = MemberDTO.builder()
                .id(1L)
                .email("test@test.com")
                .password("test1234")
                .name("park")
                .phone("010-1234-1234")
                .role(Role.USER)
                .build();

        Guesthouse guesthouse = Guesthouse.builder()
                .guesthouseName("kim guesthouse")
                .ownerName("kim")
                .regionCode("0101")
                .detailAddress("OO로 XXX")
                .description("test요약")
                .build();

        Field idField = Guesthouse.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(guesthouse, guesthouseId);

        when(guesthouseRepository.findById(guesthouseId)).thenReturn(Optional.of(guesthouse));
        when(memberService.findMemberByAuth()).thenReturn(memberDTO);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.createReview(guesthouseId,reviewEnrollRequest);

        assertNotNull(result, "리뷰 생성 결과는 null이 아니어야 합니다.");
        assertEquals(reviewDTO.getContent(), result.getContent(), "리뷰의 내용이 기대한 값과 일치해야 합니다.");

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
