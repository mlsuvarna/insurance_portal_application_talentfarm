package com.insurance.insuranceportal.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PolicyPurchaseRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than zero")
    private Long userId;

    @NotNull(message = "Policy type ID is required")
    @Positive(message = "Policy type ID must be greater than zero")
    private Long policyTypeId;

    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage amount must be greater than zero")
    private BigDecimal coverageAmount;

    public PolicyPurchaseRequest() {
    }

    public PolicyPurchaseRequest(
            Long userId,
            Long policyTypeId,
            BigDecimal coverageAmount
    ) {
        this.userId = userId;
        this.policyTypeId = policyTypeId;
        this.coverageAmount = coverageAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPolicyTypeId() {
        return policyTypeId;
    }

    public void setPolicyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public BigDecimal getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(
            BigDecimal coverageAmount
    ) {
        this.coverageAmount = coverageAmount;
    }
}