package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.PaymentRequest;
import com.insurance.insuranceportal.dto.PaymentResponse;
import com.insurance.insuranceportal.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService
    ) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(
            @Valid @RequestBody PaymentRequest request
    ) {

        PaymentResponse response =
                paymentService.makePayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long paymentId
    ) {

        PaymentResponse response =
                paymentService.getPaymentById(paymentId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reference/{paymentReference}")
    public ResponseEntity<PaymentResponse>
    getPaymentByReference(
            @PathVariable String paymentReference
    ) {

        PaymentResponse response =
                paymentService.getPaymentByReference(
                        paymentReference
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<PaymentResponse>>
    getPaymentsByPolicy(
            @PathVariable Long policyId
    ) {

        List<PaymentResponse> responses =
                paymentService.getPaymentsByPolicy(
                        policyId
                );

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>>
    getPaymentsByUser(
            @PathVariable Long userId
    ) {

        List<PaymentResponse> responses =
                paymentService.getPaymentsByUser(userId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponse>>
    getPaymentsByStatus(
            @PathVariable String status
    ) {

        List<PaymentResponse> responses =
                paymentService.getPaymentsByStatus(status);

        return ResponseEntity.ok(responses);
    }
}
