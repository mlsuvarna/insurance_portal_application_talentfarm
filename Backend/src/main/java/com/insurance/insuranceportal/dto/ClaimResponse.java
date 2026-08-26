package com.insurance.insuranceportal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClaimResponse {

    private Long claimId;

    private String claimNumber;

    private Long policyId;

    private String policyNumber;

    private Long userId;

    private String customerName;

    private BigDecimal claimAmount;

    private String claimReason;

    private LocalDate incidentDate;

    private String status;

    private String remarks;

    private LocalDateTime submittedAt;

    private LocalDateTime updatedAt;

    private String message;

    public ClaimResponse() {
    }

    public ClaimResponse(
            Long claimId,
            String claimNumber,
            Long policyId,
            String policyNumber,
            Long userId,
            String customerName,
            BigDecimal claimAmount,
            String claimReason,
            LocalDate incidentDate,
            String status,
            String remarks,
            LocalDateTime submittedAt,
            LocalDateTime updatedAt,
            String message
    ) {
        this.claimId = claimId;
        this.claimNumber = claimNumber;
        this.policyId = policyId;
        this.policyNumber = policyNumber;
        this.userId = userId;
        this.customerName = customerName;
        this.claimAmount = claimAmount;
        this.claimReason = claimReason;
        this.incidentDate = incidentDate;
        this.status = status;
        this.remarks = remarks;
        this.submittedAt = submittedAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(
            String policyNumber
    ) {
        this.policyNumber = policyNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(
            String customerName
    ) {
        this.customerName = customerName;
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

    public void setClaimReason(
            String claimReason
    ) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(
            LocalDateTime submittedAt
    ) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}