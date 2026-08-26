package com.insurance.insuranceportal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PolicyResponse {

    private Long policyId;

    private String policyNumber;

    private Long userId;

    private String customerName;

    private Long policyTypeId;

    private String policyTypeName;

    private BigDecimal premiumAmount;

    private BigDecimal coverageAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private LocalDateTime createdAt;

    private String message;

    public PolicyResponse() {
    }

    public PolicyResponse(
            Long policyId,
            String policyNumber,
            Long userId,
            String customerName,
            Long policyTypeId,
            String policyTypeName,
            BigDecimal premiumAmount,
            BigDecimal coverageAmount,
            LocalDate startDate,
            LocalDate endDate,
            String status,
            LocalDateTime createdAt,
            String message
    ) {
        this.policyId = policyId;
        this.policyNumber = policyNumber;
        this.userId = userId;
        this.customerName = customerName;
        this.policyTypeId = policyTypeId;
        this.policyTypeName = policyTypeName;
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.message = message;
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

    public void setPolicyNumber(String policyNumber) {
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

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getPolicyTypeId() {
        return policyTypeId;
    }

    public void setPolicyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public String getPolicyTypeName() {
        return policyTypeName;
    }

    public void setPolicyTypeName(String policyTypeName) {
        this.policyTypeName = policyTypeName;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(
            BigDecimal premiumAmount
    ) {
        this.premiumAmount = premiumAmount;
    }

    public BigDecimal getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(
            BigDecimal coverageAmount
    ) {
        this.coverageAmount = coverageAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}