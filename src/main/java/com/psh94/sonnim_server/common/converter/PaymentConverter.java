package com.psh94.sonnim_server.common.converter;

import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;

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

    public static Payment toEntity(String paymentMethod, int totalPrice, Reservation reservation) {
        return Payment.builder()
                .paymentMethod(paymentMethod)
                .totalPrice(totalPrice)
                .paymentStatus(PaymentStatus.PENDING)
                .reservation(reservation)
                .build();
    }
}