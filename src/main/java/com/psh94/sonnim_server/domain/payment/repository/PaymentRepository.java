package com.psh94.sonnim_server.domain.payment.repository;

import com.psh94.sonnim_server.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
