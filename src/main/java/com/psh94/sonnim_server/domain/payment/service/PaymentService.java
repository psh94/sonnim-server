package com.psh94.sonnim_server.domain.payment.service;

import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO createPayment(PaymentCreateRequest paymentRequest);
    PaymentDTO getPaymentById(Long paymentId);
    void completePayment(Long paymentId);
    void cancelPayment(Long paymentId);

}
