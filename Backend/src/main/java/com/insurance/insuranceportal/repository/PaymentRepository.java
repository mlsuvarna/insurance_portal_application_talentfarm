package com.insurance.insuranceportal.repository;

import com.insurance.insuranceportal.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(
            String paymentReference
    );

    boolean existsByPaymentReference(
            String paymentReference
    );

    List<Payment> findByPolicyPolicyId(
            Long policyId
    );

    List<Payment> findByStatus(
            String status
    );

    List<Payment> findByPolicyUserUserId(
            Long userId
    );
}