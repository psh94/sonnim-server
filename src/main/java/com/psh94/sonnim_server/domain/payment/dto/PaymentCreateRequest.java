package com.psh94.sonnim_server.domain.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {

    @NotNull
    private Long reservationId;

    @NotBlank
    private String paymentMethod;

    @NotBlank
    private int totalPrice;
}
