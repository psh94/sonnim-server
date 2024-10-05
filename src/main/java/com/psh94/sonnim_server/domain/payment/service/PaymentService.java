package com.psh94.sonnim_server.domain.payment.service;

import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    PaymentDTO createPayment(PaymentCreateRequest paymentRequest);
    PaymentDTO getPaymentById(Long paymentId);

    List<PaymentDTO> getPaymentsByMemberId(Long memberId);
    void completePayment(Long paymentId);
    void cancelPayment(Long paymentId);

}
