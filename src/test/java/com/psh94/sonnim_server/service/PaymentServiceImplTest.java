package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.converter.PaymentConverter;
import com.psh94.sonnim_server.domain.payment.domain.Payment;
import com.psh94.sonnim_server.domain.payment.domain.PaymentStatus;
import com.psh94.sonnim_server.domain.payment.dto.PaymentCreateRequest;
import com.psh94.sonnim_server.domain.payment.dto.PaymentDTO;
import com.psh94.sonnim_server.domain.payment.repository.PaymentRepository;
import com.psh94.sonnim_server.domain.payment.service.PaymentServiceImpl;
import com.psh94.sonnim_server.domain.reservation.entity.Reservation;
import com.psh94.sonnim_server.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private PaymentCreateRequest paymentCreateRequest;
    private Payment payment;
    private Reservation reservation;
    private PaymentDTO paymentDTO;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder()
                .build();

        paymentCreateRequest = PaymentCreateRequest.builder()
                .reservationId(1L)
                .paymentMethod("CREDIT_CARD")
                .totalPrice(10000)
                .build();

        payment = Payment.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .reservation(reservation)
                .totalPrice(10000)
                .build();

        paymentDTO = PaymentConverter.toDTO(payment);
    }

    @Test
    void 결제_생성_성공() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDTO result = paymentService.createPayment(paymentCreateRequest);

        assertNotNull(result);
        assertEquals(paymentDTO.getTotalPrice(), result.getTotalPrice());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void 결제_생성_예약_조회_실패() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(paymentCreateRequest);
        });
    }

    @Test
    void 결제_조회_성공() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        PaymentDTO result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(paymentDTO.getTotalPrice(), result.getTotalPrice());
        verify(paymentRepository, times(1)).findById(anyLong());
    }

    @Test
    void 결제_조회_실패() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.getPaymentById(1L);
        });
    }

    @Test
    void 결제_완료_성공() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        paymentService.completePayment(1L);

        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
        verify(paymentRepository, times(1)).findById(anyLong());
    }

    @Test
    void 결제_완료_실패_잘못된_상태() {
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        assertThrows(IllegalStateException.class, () -> {
            paymentService.completePayment(1L);
        });
    }

    @Test
    void 결제_취소_성공() {
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        paymentService.cancelPayment(1L);

        assertEquals(PaymentStatus.CANCELLED, payment.getPaymentStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void 결제_취소_실패_잘못된_상태() {
        payment.setPaymentStatus(PaymentStatus.PENDING);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        assertThrows(IllegalStateException.class, () -> {
            paymentService.cancelPayment(1L);
        });
    }
}