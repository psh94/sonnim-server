package com.psh94.sonnim_server.domain.payment.dto;

import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private String paymentMethod;
    private int totalPrice;
    private PaymentStatus paymentStatus;
    private Long reservationId;
}
