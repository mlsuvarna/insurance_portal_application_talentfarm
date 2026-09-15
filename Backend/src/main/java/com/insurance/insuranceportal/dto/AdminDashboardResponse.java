package com.insurance.insuranceportal.dto;

public class AdminDashboardResponse {

    private long policyTypeCount;

    private long policyCount;

    private long claimCount;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long policyTypeCount,
            long policyCount,
            long claimCount
    ) {
        this.policyTypeCount = policyTypeCount;
        this.policyCount = policyCount;
        this.claimCount = claimCount;
    }

    public long getPolicyTypeCount() {
        return policyTypeCount;
    }

    public void setPolicyTypeCount(
            long policyTypeCount
    ) {
        this.policyTypeCount = policyTypeCount;
    }

    public long getPolicyCount() {
        return policyCount;
    }

    public void setPolicyCount(
            long policyCount
    ) {
        this.policyCount = policyCount;
    }

    public long getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(
            long claimCount
    ) {
        this.claimCount = claimCount;
    }
}