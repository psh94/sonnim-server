package com.psh94.sonnim_server.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {

    private Long reservationId;
    private String paymentMethod;
    private int totalPrice;
}
