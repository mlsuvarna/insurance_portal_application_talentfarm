package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.PolicyPurchaseRequest;
import com.insurance.insuranceportal.dto.PolicyResponse;
import com.insurance.insuranceportal.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(
            PolicyService policyService
    ) {
        this.policyService = policyService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PolicyResponse> purchasePolicy(
            @Valid @RequestBody
            PolicyPurchaseRequest request
    ) {

        PolicyResponse response =
                policyService.purchasePolicy(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{policyId}")
    public ResponseEntity<PolicyResponse> getPolicyById(
            @PathVariable Long policyId
    ) {

        PolicyResponse response =
                policyService.getPolicyById(policyId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PolicyResponse>>
    getPoliciesByUser(
            @PathVariable Long userId
    ) {

        List<PolicyResponse> responses =
                policyService.getPoliciesByUser(userId);

        return ResponseEntity.ok(responses);
    }
}