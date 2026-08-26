package com.insurance.insuranceportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClaimStatusUpdateRequest {

    @NotBlank(message = "Claim status is required")
    private String status;

    @Size(
            max = 500,
            message = "Remarks cannot exceed 500 characters"
    )
    private String remarks;

    public ClaimStatusUpdateRequest() {
    }

    public ClaimStatusUpdateRequest(
            String status,
            String remarks
    ) {
        this.status = status;
        this.remarks = remarks;
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
}