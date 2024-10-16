package com.psh94.sonnim_server.domain.review.repository;

import com.psh94.sonnim_server.domain.review.entity.QReview;
import com.psh94.sonnim_server.domain.review.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findReviewByMemberId(Long memberId) {
        QReview review = QReview.review;

        return queryFactory
                .selectFrom(review)
                .where(review.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<Review> findReviewByGuesthouseId(Long guesthouseId) {
        QReview review = QReview.review;

        return queryFactory
                .selectFrom(review)
                .where(review.guesthouse.id.eq(guesthouseId))
                .fetch();
    }
}
