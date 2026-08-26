package com.insurance.insuranceportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "Policy ID is required")
    @Positive(message = "Policy ID must be greater than zero")
    private Long policyId;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required")
    @Size(
            max = 30,
            message = "Payment method cannot exceed 30 characters"
    )
    private String paymentMethod;

    public PaymentRequest() {
    }

    public PaymentRequest(
            Long policyId,
            BigDecimal amount,
            String paymentMethod
    ) {
        this.policyId = policyId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod
    ) {
        this.paymentMethod = paymentMethod;
    }
}