package com.psh94.sonnim_server.domain.payment.service;

import com.psh94.sonnim_server.common.converter.PaymentConverter;
import com.psh94.sonnim_server.common.converter.ReservationConverter;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.payment.repository.PaymentRepository;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.entity.ReservationStatus;
import com.psh94.sonnim_server.domain.reservation.repository.ReservationRepository;
import com.psh94.sonnim_server.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Transactional
    public PaymentDTO createPayment(PaymentCreateRequest paymentRequest) {
        Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        Payment payment = PaymentConverter.toEntity(
                paymentRequest.getPaymentMethod(),
                paymentRequest.getTotalPrice(),
                reservation,
                reservation.getMember()
        );

        Payment savedPayment = paymentRepository.save(payment);
        return PaymentConverter.toDTO(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        return PaymentConverter.toDTO(payment);  // Entity -> DTO 변환
    }

    @Override
    public List<PaymentDTO> getPaymentsByMemberId(Long memberId) {

        List<Payment> payments = paymentRepository.findPaymentsByMemberId(memberId);

        return payments.stream()
                .map(PaymentConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findById(payment.getReservation().getId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("결제를 대기상태가 아닙니다.");
        }

        if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("예약이 대기상태가 아닙니다.");
        }

        // 결제완료, 예약승인 상태로 변경
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
    }

    @Transactional
    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findById(payment.getReservation().getId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

        if (payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("결제가 완료상태가 아닙니다.");
        }

        payment.cancel();  // 결제 취소 메서드 호출
        reservationService.cancelReservationById(reservation.getId());
        paymentRepository.save(payment);
    }
}
