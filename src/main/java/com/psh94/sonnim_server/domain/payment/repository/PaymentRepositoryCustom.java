package com.psh94.sonnim_server.domain.payment.repository;

import com.psh94.sonnim_server.domain.payment.domain.Payment;

import java.util.List;

public interface PaymentRepositoryCustom {

    List<Payment> findPaymentsByMemberId(Long memberId);
}
