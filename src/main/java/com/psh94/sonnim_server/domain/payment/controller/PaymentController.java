package com.psh94.sonnim_server.domain.payment.controller;

import com.psh94.sonnim_server.common.auth.CheckRole;
import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 생성
    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@RequestBody PaymentCreateRequest paymentRequest) {
        PaymentDTO paymentDTO = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(paymentDTO);
    }

    // 결제 완료 처리
    @PostMapping("/payments/complete/{paymentId}")
    public ResponseEntity<?> completePayment(@PathVariable Long paymentId) {
        paymentService.completePayment(paymentId);
        return ResponseEntity.ok("Payment completed successfully.");
    }

    // 결제내역 조회
    @GetMapping("/members/{memberId}/payments")
    public ResponseEntity<?> getPaymentsByMemberId(@PathVariable Long memberId){
        List<PaymentDTO> payments = paymentService.getPaymentsByMemberId(memberId);
        return ResponseEntity.ok(payments);
    }

    // 결제 취소 처리
    @PostMapping("/payments/cancel/{paymentId}")
    @CheckRole({"AdMIN"})
    public ResponseEntity<?> cancelPayment(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("Payment canceled successfully.");
    }

    // 특정 결제 정보 조회
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long paymentId) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }
}