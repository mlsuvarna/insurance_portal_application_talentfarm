package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.PaymentRequest;
import com.insurance.insuranceportal.dto.PaymentResponse;
import com.insurance.insuranceportal.entity.Payment;
import com.insurance.insuranceportal.entity.Policy;
import com.insurance.insuranceportal.exception.PaymentNotFoundException;
import com.insurance.insuranceportal.exception.PolicyNotFoundException;
import com.insurance.insuranceportal.repository.PaymentRepository;
import com.insurance.insuranceportal.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentService.class);

    private static final Set<String> ALLOWED_PAYMENT_METHODS =
            Set.of(
                    "UPI",
                    "CARD",
                    "NET_BANKING",
                    "CASH"
            );

    private final PaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            PolicyRepository policyRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.policyRepository = policyRepository;
    }

    @Transactional
    public PaymentResponse makePayment(
            PaymentRequest request
    ) {

        log.info(
                "Payment request received for policyId: {}",
                request.getPolicyId()
        );

        Policy policy = policyRepository
                .findById(request.getPolicyId())
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with ID: "
                                        + request.getPolicyId()
                        ));

        validatePayment(policy, request);

        String paymentMethod =
                request.getPaymentMethod()
                        .trim()
                        .toUpperCase();

        String paymentReference =
                generatePaymentReference();

        String transactionId =
                generateTransactionId();

        Payment payment = new Payment(
                paymentReference,
                policy,
                request.getAmount(),
                paymentMethod,
                "SUCCESS",
                transactionId,
                LocalDateTime.now()
        );

        Payment savedPayment =
                paymentRepository.save(payment);

        log.info(
                "Payment completed successfully. "
                        + "paymentId: {}, reference: {}",
                savedPayment.getPaymentId(),
                savedPayment.getPaymentReference()
        );

        return convertToResponse(
                savedPayment,
                "Payment completed successfully"
        );
    }

    public PaymentResponse getPaymentById(
            Long paymentId
    ) {

        Payment payment = findPaymentById(paymentId);

        return convertToResponse(
                payment,
                "Payment retrieved successfully"
        );
    }

    public PaymentResponse getPaymentByReference(
            String paymentReference
    ) {

        Payment payment = paymentRepository
                .findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with reference: "
                                        + paymentReference
                        ));

        return convertToResponse(
                payment,
                "Payment retrieved successfully"
        );
    }

    public List<PaymentResponse> getPaymentsByPolicy(
            Long policyId
    ) {

        if (!policyRepository.existsById(policyId)) {
            throw new PolicyNotFoundException(
                    "Policy not found with ID: "
                            + policyId
            );
        }

        return paymentRepository
                .findByPolicyPolicyId(policyId)
                .stream()
                .map(payment -> convertToResponse(
                        payment,
                        "Payment retrieved successfully"
                ))
                .toList();
    }

    public List<PaymentResponse> getPaymentsByUser(
            Long userId
    ) {

        return paymentRepository
                .findByPolicyUserUserId(userId)
                .stream()
                .map(payment -> convertToResponse(
                        payment,
                        "Payment retrieved successfully"
                ))
                .toList();
    }

    public List<PaymentResponse> getPaymentsByStatus(
            String status
    ) {

        String normalizedStatus =
                status.trim().toUpperCase();

        return paymentRepository
                .findByStatus(normalizedStatus)
                .stream()
                .map(payment -> convertToResponse(
                        payment,
                        "Payment retrieved successfully"
                ))
                .toList();
    }

    private Payment findPaymentById(Long paymentId) {

        return paymentRepository
                .findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with ID: "
                                        + paymentId
                        ));
    }

    private void validatePayment(
            Policy policy,
            PaymentRequest request
    ) {

        if (!"ACTIVE".equalsIgnoreCase(
                policy.getStatus()
        )) {
            throw new IllegalStateException(
                    "Payments can be made only for active policies"
            );
        }

        String paymentMethod =
                request.getPaymentMethod()
                        .trim()
                        .toUpperCase();

        if (!ALLOWED_PAYMENT_METHODS.contains(
                paymentMethod
        )) {
            throw new IllegalStateException(
                    "Invalid payment method. "
                            + "Allowed methods are UPI, CARD, "
                            + "NET_BANKING and CASH"
            );
        }

        if (request.getAmount().compareTo(
                policy.getPremiumAmount()
        ) != 0) {
            throw new IllegalStateException(
                    "Payment amount must match the policy premium amount"
            );
        }
    }

    private String generatePaymentReference() {

        String paymentReference;

        do {
            String reference = UUID
                    .randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();

            paymentReference =
                    "PAY-"
                            + Year.now().getValue()
                            + "-"
                            + reference;

        } while (paymentRepository
                .existsByPaymentReference(
                        paymentReference
                ));

        return paymentReference;
    }

    private String generateTransactionId() {

        String reference = UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();

        return "TXN-" + reference;
    }

    private PaymentResponse convertToResponse(
            Payment payment,
            String message
    ) {

        String customerName =
                payment.getPolicy()
                        .getUser()
                        .getFirstName()
                        + " "
                        + payment.getPolicy()
                        .getUser()
                        .getLastName();

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getPaymentReference(),
                payment.getPolicy().getPolicyId(),
                payment.getPolicy().getPolicyNumber(),
                payment.getPolicy().getUser().getUserId(),
                customerName,
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getPaidAt(),
                message
        );
    }
}