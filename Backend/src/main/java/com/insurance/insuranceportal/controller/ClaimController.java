package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.ClaimCreateRequest;
import com.insurance.insuranceportal.dto.ClaimResponse;
import com.insurance.insuranceportal.dto.ClaimStatusUpdateRequest;
import com.insurance.insuranceportal.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(
            ClaimService claimService
    ) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<ClaimResponse> submitClaim(
            @Valid @RequestBody
            ClaimCreateRequest request
    ) {

        ClaimResponse response =
                claimService.submitClaim(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ClaimResponse> getClaimById(
            @PathVariable Long claimId
    ) {

        ClaimResponse response =
                claimService.getClaimById(claimId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<ClaimResponse>>
    getClaimsByPolicy(
            @PathVariable Long policyId
    ) {

        List<ClaimResponse> responses =
                claimService.getClaimsByPolicy(policyId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClaimResponse>>
    getClaimsByUser(
            @PathVariable Long userId
    ) {

        List<ClaimResponse> responses =
                claimService.getClaimsByUser(userId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClaimResponse>>
    getClaimsByStatus(
            @PathVariable String status
    ) {

        List<ClaimResponse> responses =
                claimService.getClaimsByStatus(status);

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{claimId}/status")
    public ResponseEntity<ClaimResponse>
    updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody
            ClaimStatusUpdateRequest request
    ) {

        ClaimResponse response =
                claimService.updateClaimStatus(
                        claimId,
                        request
                );

        return ResponseEntity.ok(response);
    }
}