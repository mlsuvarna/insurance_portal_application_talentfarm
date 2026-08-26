package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.PolicyTypeRequest;
import com.insurance.insuranceportal.dto.PolicyTypeResponse;
import com.insurance.insuranceportal.entity.PolicyType;
import com.insurance.insuranceportal.exception.PolicyTypeAlreadyExistsException;
import com.insurance.insuranceportal.exception.PolicyTypeNotFoundException;
import com.insurance.insuranceportal.repository.PolicyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PolicyTypeService {

    private final PolicyTypeRepository policyTypeRepository;

    public PolicyTypeService(
            PolicyTypeRepository policyTypeRepository
    ) {
        this.policyTypeRepository = policyTypeRepository;
    }
    public PolicyTypeResponse createPolicyType(
            PolicyTypeRequest request
    ) {

        if (policyTypeRepository.existsByTypeName(
                request.getTypeName())) {

            throw new PolicyTypeAlreadyExistsException(
                    "Policy type already exists"
            );
        }

        PolicyType policyType = new PolicyType(
                request.getTypeName(),
                request.getDescription(),
                request.getBasePremium(),
                request.getDefaultCoverageAmount(),
                request.getActive()
        );

        PolicyType savedPolicyType =
                policyTypeRepository.save(policyType);

        return new PolicyTypeResponse(
                savedPolicyType.getPolicyTypeId(),
                savedPolicyType.getTypeName(),
                savedPolicyType.getDescription(),
                savedPolicyType.getBasePremium(),
                savedPolicyType.getDefaultCoverageAmount(),
                savedPolicyType.isActive(),
                "Policy type created successfully"
        );
    }
    public PolicyType findPolicyTypeById(
            Long policyTypeId
    ) {

        return policyTypeRepository
                .findById(policyTypeId)
                .orElseThrow(() ->
                        new PolicyTypeNotFoundException(
                                "Policy type not found with ID: "
                                        + policyTypeId
                        ));
    }
    public List<PolicyType> getActivePolicyTypes() {

        return policyTypeRepository
                .findByActiveTrue();
    }
}