package com.psh94.sonnim_server.domain.payment.domain;

import com.psh94.sonnim_server.common.utils.BaseTimeEntity;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private int totalPrice;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;  // 결제 상태 추가

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Payment(String paymentMethod, int totalPrice, PaymentStatus paymentStatus, Reservation reservation) {
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.reservation = reservation;
    }

    // 결제 취소 메서드
    public void cancel() {
        if (this.paymentStatus == PaymentStatus.CANCELED) {
            throw new IllegalStateException("Payment is already canceled");
        }
        this.paymentStatus = PaymentStatus.CANCELED;
    }
}