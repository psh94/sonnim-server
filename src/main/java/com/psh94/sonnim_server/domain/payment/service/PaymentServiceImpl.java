package com.psh94.sonnim_server.domain.payment.service;

import com.psh94.sonnim_server.common.converter.PaymentConverter;
import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.payment.repository.PaymentRepository;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public PaymentDTO createPayment(PaymentCreateRequest paymentRequest) {
        Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Payment payment = PaymentConverter.toEntity(
                paymentRequest.getPaymentMethod(),
                paymentRequest.getTotalPrice(),
                reservation
        );

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentConverter.toDTO(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return PaymentConverter.toDTO(payment);  // Entity -> DTO 변환
    }

    @Transactional
    public void completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not in pending status");
        }

        // 결제 완료 상태로 변경
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
    }

    @Transactional
    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        payment.cancel();  // 결제 취소 메서드 호출
        paymentRepository.save(payment);
    }
}
