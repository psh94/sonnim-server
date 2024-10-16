package com.psh94.sonnim_server.domain.payment.dto;

import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String paymentMethod;

    @NotBlank
    private int totalPrice;

    @NotBlank
    private PaymentStatus paymentStatus;

    @NotNull
    private Long reservationId;
}
