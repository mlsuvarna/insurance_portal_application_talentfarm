package com.insurance.insuranceportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "policy_types")
public class PolicyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_type_id")
    private Long policyTypeId;

    @Column(
            name = "type_name",
            nullable = false,
            unique = true,
            length = 100
    )
    private String typeName;

    @Column(
            name = "description",
            nullable = false,
            length = 500
    )
    private String description;

    @Column(
            name = "base_premium",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal basePremium;

    @Column(
            name = "default_coverage_amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal defaultCoverageAmount;

    @Column(
            name = "active",
            nullable = false
    )
    private boolean active;

    public PolicyType() {
    }

    public PolicyType(
            String typeName,
            String description,
            BigDecimal basePremium,
            BigDecimal defaultCoverageAmount,
            boolean active
    ) {
        this.typeName = typeName;
        this.description = description;
        this.basePremium = basePremium;
        this.defaultCoverageAmount =
                defaultCoverageAmount;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}