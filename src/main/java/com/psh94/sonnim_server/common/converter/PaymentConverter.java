package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.entity.Member;
import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.reservation.dto.ReservationRequest;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;

public class PaymentConverter {

    public static PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getPaymentMethod(),
                payment.getTotalPrice(),
                payment.getPaymentStatus(),
                payment.getReservation().getId()
        );
    }



    public static Payment toEntity(String paymentMethod, int totalPrice, Reservation reservation, Member member) {
        return Payment.builder()
                .paymentMethod(paymentMethod)
                .totalPrice(totalPrice)
                .paymentStatus(PaymentStatus.PENDING)
                .reservation(reservation)
                .member(member)
                .build();
    }
}