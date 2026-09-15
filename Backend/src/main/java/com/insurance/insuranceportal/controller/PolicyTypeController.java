package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.PolicyTypeRequest;
import com.insurance.insuranceportal.dto.PolicyTypeResponse;
import com.insurance.insuranceportal.entity.PolicyType;
import com.insurance.insuranceportal.service.PolicyTypeService;
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
@RequestMapping("/api/policy-types")
public class PolicyTypeController {

    private final PolicyTypeService policyTypeService;

    public PolicyTypeController(
            PolicyTypeService policyTypeService
    ) {
        this.policyTypeService = policyTypeService;
    }

    @PostMapping
    public ResponseEntity<PolicyTypeResponse>
    createPolicyType(
            @Valid @RequestBody PolicyTypeRequest request
    ) {

        PolicyTypeResponse response =
                policyTypeService.createPolicyType(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PolicyTypeResponse>>
    getActivePolicyTypes() {

        List<PolicyTypeResponse> responses =
                policyTypeService
                        .getActivePolicyTypes()
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{policyTypeId}")
    public ResponseEntity<PolicyTypeResponse>
    getPolicyTypeById(
            @PathVariable Long policyTypeId
    ) {

        PolicyType policyType =
                policyTypeService.findPolicyTypeById(
                        policyTypeId
                );

        return ResponseEntity.ok(
                convertToResponse(policyType)
        );
    }

    private PolicyTypeResponse convertToResponse(
            PolicyType policyType
    ) {

        return new PolicyTypeResponse(
                policyType.getPolicyTypeId(),
                policyType.getTypeName(),
                policyType.getDescription(),
                policyType.getCategory(),
                policyType.getBasePremium(),
                policyType.getDefaultCoverageAmount(),
                policyType.getTenureMonths(),
                policyType.isActive(),
                "Policy type retrieved successfully"
        );
    }
}