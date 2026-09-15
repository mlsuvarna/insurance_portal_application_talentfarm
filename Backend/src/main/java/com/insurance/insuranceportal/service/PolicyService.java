package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.PolicyPurchaseRequest;
import com.insurance.insuranceportal.dto.PolicyResponse;
import com.insurance.insuranceportal.entity.Policy;
import com.insurance.insuranceportal.entity.PolicyType;
import com.insurance.insuranceportal.entity.User;
import com.insurance.insuranceportal.exception.PolicyNotFoundException;
import com.insurance.insuranceportal.exception.PolicyTypeNotFoundException;
import com.insurance.insuranceportal.exception.UserNotFoundException;
import com.insurance.insuranceportal.repository.PolicyRepository;
import com.insurance.insuranceportal.repository.PolicyTypeRepository;
import com.insurance.insuranceportal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class PolicyService {

    private static final Logger log =
            LoggerFactory.getLogger(PolicyService.class);

    private final PolicyRepository policyRepository;
    private final UserRepository userRepository;
    private final PolicyTypeRepository policyTypeRepository;

    public PolicyService(
            PolicyRepository policyRepository,
            UserRepository userRepository,
            PolicyTypeRepository policyTypeRepository
    ) {
        this.policyRepository = policyRepository;
        this.userRepository = userRepository;
        this.policyTypeRepository = policyTypeRepository;
    }

    @Transactional
    public PolicyResponse purchasePolicy(
            PolicyPurchaseRequest request
    ) {

        log.info(
                "Policy purchase request received for userId: {}",
                request.getUserId()
        );

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with ID: "
                                + request.getUserId()
                ));

        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new IllegalStateException(
                    "Only active users can purchase policies"
            );
        }

        PolicyType policyType = policyTypeRepository
                .findById(request.getPolicyTypeId())
                .orElseThrow(() ->
                        new PolicyTypeNotFoundException(
                                "Policy type not found with ID: "
                                        + request.getPolicyTypeId()
                        ));

        if (!policyType.isActive()) {
            throw new IllegalStateException(
                    "Selected policy type is not available"
            );
        }

        String policyNumber = generatePolicyNumber();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate
                .plusMonths(policyType.getTenureMonths())
                .minusDays(1);

        Policy policy = new Policy(
                policyNumber,
                user,
                policyType,
                policyType.getBasePremium(),
                request.getCoverageAmount(),
                startDate,
                endDate,
                "ACTIVE",
                LocalDateTime.now()
        );

        Policy savedPolicy =
                policyRepository.save(policy);

        log.info(
                "Policy purchased successfully. policyId: {}, policyNumber: {}",
                savedPolicy.getPolicyId(),
                savedPolicy.getPolicyNumber()
        );

        return convertToResponse(
                savedPolicy,
                "Policy purchased successfully"
        );
    }

    public PolicyResponse getPolicyById(Long policyId) {

        Policy policy = policyRepository
                .findById(policyId)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with ID: "
                                        + policyId
                        ));

        return convertToResponse(
                policy,
                "Policy retrieved successfully"
        );
    }

    public List<PolicyResponse> getPoliciesByUser(
            Long userId
    ) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        return policyRepository
                .findByUserUserId(userId)
                .stream()
                .map(policy -> convertToResponse(
                        policy,
                        "Policy retrieved successfully"
                ))
                .toList();
    }

    private String generatePolicyNumber() {

        String policyNumber;

        do {
            String reference = UUID
                    .randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();

            policyNumber =
                    "POL-"
                            + Year.now().getValue()
                            + "-"
                            + reference;

        } while (policyRepository
                .existsByPolicyNumber(policyNumber));

        return policyNumber;
    }

    private PolicyResponse convertToResponse(
            Policy policy,
            String message
    ) {

        String customerName =
                policy.getUser().getFirstName()
                        + " "
                        + policy.getUser().getLastName();

        return new PolicyResponse(
                policy.getPolicyId(),
                policy.getPolicyNumber(),
                policy.getUser().getUserId(),
                customerName,
                policy.getPolicyType().getPolicyTypeId(),
                policy.getPolicyType().getTypeName(),
                policy.getPremiumAmount(),
                policy.getCoverageAmount(),
                policy.getStartDate(),
                policy.getEndDate(),
                policy.getStatus(),
                policy.getCreatedAt(),
                message
        );
    }
}