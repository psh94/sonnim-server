package com.psh94.sonnim_server.domain.review.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private int score;  // 1~5점
    private String title;  // 리뷰 제목
    private String content;  // 리뷰 내용
}
