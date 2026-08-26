package com.insurance.insuranceportal.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PolicyTypeRequest {

    @NotBlank(message = "Policy type name is required")
    @Size(
            max = 100,
            message = "Policy type name cannot exceed 100 characters"
    )
    private String typeName;

    @NotBlank(message = "Description is required")
    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Base premium is required")
    @DecimalMin(
            value = "0.01",
            message = "Base premium must be greater than zero"
    )
    @Digits(
            integer = 10,
            fraction = 2,
            message = "Base premium must have up to 10 digits and 2 decimal places"
    )
    private BigDecimal basePremium;

    @NotNull(message = "Default coverage amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Default coverage amount must be greater than zero"
    )
    @Digits(
            integer = 13,
            fraction = 2,
            message = "Coverage amount must have up to 13 digits and 2 decimal places"
    )
    private BigDecimal defaultCoverageAmount;

    @NotNull(message = "Active status is required")
    private Boolean active;

    public PolicyTypeRequest() {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}