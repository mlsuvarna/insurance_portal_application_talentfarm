package com.insurance.insuranceportal.dto;

import java.math.BigDecimal;

public class PolicyTypeResponse {

    private Long policyTypeId;

    private String typeName;

    private String description;

    private String category;

    private BigDecimal basePremium;

    private BigDecimal defaultCoverageAmount;

    private Integer tenureMonths;

    private boolean active;

    private String message;

    public PolicyTypeResponse() {
    }

    public PolicyTypeResponse(
            Long policyTypeId,
            String typeName,
            String description,
            String category,
            BigDecimal basePremium,
            BigDecimal defaultCoverageAmount,
            Integer tenureMonths,
            boolean active,
            String message
    ) {
        this.policyTypeId = policyTypeId;
        this.typeName = typeName;
        this.description = description;
        this.category=category;
        this.basePremium = basePremium;
        this.defaultCoverageAmount =
                defaultCoverageAmount;
        this.tenureMonths = tenureMonths;
        this.active = active;
        this.message = message;
    }

    public Long getPolicyTypeId() {
        return policyTypeId;
    }

    public void setPolicyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(BigDecimal basePremium) {
        this.basePremium = basePremium;
    }

    public BigDecimal getDefaultCoverageAmount() {
        return defaultCoverageAmount;
    }

    public void setDefaultCoverageAmount(
            BigDecimal defaultCoverageAmount
    ) {
        this.defaultCoverageAmount =
                defaultCoverageAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }
}