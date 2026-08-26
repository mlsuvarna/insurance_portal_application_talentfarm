package com.insurance.insuranceportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimCreateRequest {

    @NotNull(message = "Policy ID is required")
    @Positive(message = "Policy ID must be greater than zero")
    private Long policyId;

    @NotNull(message = "Claim amount is required")
    @Positive(message = "Claim amount must be greater than zero")
    private BigDecimal claimAmount;

    @NotBlank(message = "Claim reason is required")
    @Size(
            max = 500,
            message = "Claim reason cannot exceed 500 characters"
    )
    private String claimReason;

    @NotNull(message = "Incident date is required")
    @PastOrPresent(
            message = "Incident date cannot be in the future"
    )
    private LocalDate incidentDate;

    public ClaimCreateRequest() {
    }

    public ClaimCreateRequest(
            Long policyId,
            BigDecimal claimAmount,
            String claimReason,
            LocalDate incidentDate
    ) {
        this.policyId = policyId;
        this.claimAmount = claimAmount;
        this.claimReason = claimReason;
        this.incidentDate = incidentDate;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(
            BigDecimal claimAmount
    ) {
        this.claimAmount = claimAmount;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(
            LocalDate incidentDate
    ) {
        this.incidentDate = incidentDate;
    }
}