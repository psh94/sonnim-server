package com.psh94.sonnim_server.domain.payment.repository;

import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Payment> findPaymentsByMemberId(Long memberId) {
        QPayment payment = QPayment.payment;

        return queryFactory
                .selectFrom(payment)
                .where(payment.member.id.eq(memberId))
                .fetch();
    }
}
