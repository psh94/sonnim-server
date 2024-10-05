package com.psh94.sonnim_server.domain.payment.controller;

import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 생성
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentCreateRequest paymentRequest) {
        PaymentDTO paymentDTO = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(paymentDTO);
    }

    // 결제 완료 처리
    @PostMapping("/complete/{paymentId}")
    public ResponseEntity<?> completePayment(@PathVariable Long paymentId) {
        paymentService.completePayment(paymentId);
        return ResponseEntity.ok("Payment completed successfully.");
    }

    // 결제 취소 처리
    @PostMapping("/cancel/{paymentId}")
    public ResponseEntity<?> cancelPayment(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("Payment canceled successfully.");
    }

    // 특정 결제 정보 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long paymentId) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }
}