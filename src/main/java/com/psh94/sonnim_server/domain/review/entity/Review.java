package com.psh94.sonnim_server.domain.review.entity;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "guesthouse_id")
    private Guesthouse guesthouse;

    @Builder
    public Review(int score, String title, String content, Member member, Guesthouse guesthouse) {
        this.score = score;
        this.title = title;
        this.content = content;
        this.member = member;
        this.guesthouse = guesthouse;
    }

    // 점수 유효성 검사
    private void setScore(int score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("점수는 1에서 5 사이여야 합니다.");
        }
        this.score = score;
    }
}
