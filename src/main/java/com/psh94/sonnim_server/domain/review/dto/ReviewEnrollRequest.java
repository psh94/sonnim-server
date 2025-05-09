package com.psh94.sonnim_server.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewEnrollRequest {

    @NotNull
    private int score;  // 1~5점

    @NotBlank
    private String title;  // 리뷰 제목

    @NotBlank
    private String content;  // 리뷰 내용
}
