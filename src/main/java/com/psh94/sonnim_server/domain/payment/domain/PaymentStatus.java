package com.psh94.sonnim_server.domain.payment.domain;

public enum PaymentStatus {
    PENDING,   // 결제 대기
    COMPLETED, // 결제 완료
    CANCELLED   // 결제 취소
}