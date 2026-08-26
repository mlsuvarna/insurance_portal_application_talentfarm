package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.ClaimCreateRequest;
import com.insurance.insuranceportal.dto.ClaimResponse;
import com.insurance.insuranceportal.dto.ClaimStatusUpdateRequest;
import com.insurance.insuranceportal.entity.Claim;
import com.insurance.insuranceportal.entity.Policy;
import com.insurance.insuranceportal.exception.ClaimNotFoundException;
import com.insurance.insuranceportal.exception.PolicyNotFoundException;
import com.insurance.insuranceportal.repository.ClaimRepository;
import com.insurance.insuranceportal.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ClaimService {

    private static final Logger log =
            LoggerFactory.getLogger(ClaimService.class);

    private static final Set<String> ALLOWED_REVIEW_STATUSES =
            Set.of(
                    "IN_REVIEW",
                    "APPROVED",
                    "REJECTED",
                    "SETTLED"
            );

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    public ClaimService(
            ClaimRepository claimRepository,
            PolicyRepository policyRepository
    ) {
        this.claimRepository = claimRepository;
        this.policyRepository = policyRepository;
    }

    @Transactional
    public ClaimResponse submitClaim(
            ClaimCreateRequest request
    ) {

        log.info(
                "Claim submission received for policyId: {}",
                request.getPolicyId()
        );

        Policy policy = policyRepository
                .findById(request.getPolicyId())
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found with ID: "
                                        + request.getPolicyId()
                        ));

        validatePolicyForClaim(policy, request);

        String claimNumber = generateClaimNumber();

        Claim claim = new Claim(
                claimNumber,
                policy,
                request.getClaimAmount(),
                request.getClaimReason(),
                request.getIncidentDate(),
                "SUBMITTED",
                null
        );

        Claim savedClaim =
                claimRepository.save(claim);

        log.info(
                "Claim submitted successfully. claimId: {}, claimNumber: {}",
                savedClaim.getClaimId(),
                savedClaim.getClaimNumber()
        );

        return convertToResponse(
                savedClaim,
                "Claim submitted successfully"
        );
    }

    public ClaimResponse getClaimById(Long claimId) {

        Claim claim = findClaimById(claimId);

        return convertToResponse(
                claim,
                "Claim retrieved successfully"
        );
    }

    public List<ClaimResponse> getClaimsByPolicy(
            Long policyId
    ) {

        if (!policyRepository.existsById(policyId)) {
            throw new PolicyNotFoundException(
                    "Policy not found with ID: "
                            + policyId
            );
        }

        return claimRepository
                .findByPolicyPolicyId(policyId)
                .stream()
                .map(claim -> convertToResponse(
                        claim,
                        "Claim retrieved successfully"
                ))
                .toList();
    }

    public List<ClaimResponse> getClaimsByUser(
            Long userId
    ) {

        return claimRepository
                .findByPolicyUserUserId(userId)
                .stream()
                .map(claim -> convertToResponse(
                        claim,
                        "Claim retrieved successfully"
                ))
                .toList();
    }

    public List<ClaimResponse> getClaimsByStatus(
            String status
    ) {

        String normalizedStatus =
                status.trim().toUpperCase();

        return claimRepository
                .findByStatus(normalizedStatus)
                .stream()
                .map(claim -> convertToResponse(
                        claim,
                        "Claim retrieved successfully"
                ))
                .toList();
    }

    @Transactional
    public ClaimResponse updateClaimStatus(
            Long claimId,
            ClaimStatusUpdateRequest request
    ) {

        Claim claim = findClaimById(claimId);

        String newStatus =
                request.getStatus()
                        .trim()
                        .toUpperCase();

        validateStatusUpdate(
                claim,
                newStatus,
                request.getRemarks()
        );

        claim.setStatus(newStatus);
        claim.setRemarks(request.getRemarks());
        claim.setUpdatedAt(LocalDateTime.now());

        Claim updatedClaim =
                claimRepository.save(claim);

        log.info(
                "Claim status updated. claimId: {}, status: {}",
                updatedClaim.getClaimId(),
                updatedClaim.getStatus()
        );

        return convertToResponse(
                updatedClaim,
                "Claim status updated successfully"
        );
    }

    private Claim findClaimById(Long claimId) {

        return claimRepository
                .findById(claimId)
                .orElseThrow(() ->
                        new ClaimNotFoundException(
                                "Claim not found with ID: "
                                        + claimId
                        ));
    }

    private void validatePolicyForClaim(
            Policy policy,
            ClaimCreateRequest request
    ) {

        if (!"ACTIVE".equalsIgnoreCase(
                policy.getStatus()
        )) {
            throw new IllegalStateException(
                    "Claims can be submitted only for active policies"
            );
        }

        if (request.getIncidentDate().isBefore(
                policy.getStartDate()
        )) {
            throw new IllegalStateException(
                    "Incident date cannot be before the policy start date"
            );
        }

        if (request.getIncidentDate().isAfter(
                policy.getEndDate()
        )) {
            throw new IllegalStateException(
                    "Incident date cannot be after the policy end date"
            );
        }

        if (request.getClaimAmount().compareTo(
                policy.getCoverageAmount()
        ) > 0) {
            throw new IllegalStateException(
                    "Claim amount cannot exceed the policy coverage amount"
            );
        }
    }

    private void validateStatusUpdate(
            Claim claim,
            String newStatus,
            String remarks
    ) {

        if (!ALLOWED_REVIEW_STATUSES.contains(
                newStatus
        )) {
            throw new IllegalStateException(
                    "Invalid claim status"
            );
        }

        if ("REJECTED".equals(newStatus)
                && (remarks == null
                || remarks.isBlank())) {

            throw new IllegalStateException(
                    "Remarks are required when rejecting a claim"
            );
        }

        if ("SETTLED".equals(newStatus)
                && !"APPROVED".equalsIgnoreCase(
                claim.getStatus()
        )) {
            throw new IllegalStateException(
                    "Only an approved claim can be settled"
            );
        }

        if ("REJECTED".equalsIgnoreCase(
                claim.getStatus()
        )
                || "SETTLED".equalsIgnoreCase(
                claim.getStatus()
        )) {

            throw new IllegalStateException(
                    "The claim status can no longer be changed"
            );
        }
    }

    private String generateClaimNumber() {

        String claimNumber;

        do {
            String reference = UUID
                    .randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();

            claimNumber =
                    "CLM-"
                            + Year.now().getValue()
                            + "-"
                            + reference;

        } while (claimRepository
                .existsByClaimNumber(claimNumber));

        return claimNumber;
    }

    private ClaimResponse convertToResponse(
            Claim claim,
            String message
    ) {

        String customerName =
                claim.getPolicy()
                        .getUser()
                        .getFirstName()
                        + " "
                        + claim.getPolicy()
                        .getUser()
                        .getLastName();

        return new ClaimResponse(
                claim.getClaimId(),
                claim.getClaimNumber(),
                claim.getPolicy().getPolicyId(),
                claim.getPolicy().getPolicyNumber(),
                claim.getPolicy().getUser().getUserId(),
                customerName,
                claim.getClaimAmount(),
                claim.getClaimReason(),
                claim.getIncidentDate(),
                claim.getStatus(),
                claim.getRemarks(),
                claim.getSubmittedAt(),
                claim.getUpdatedAt(),
                message
        );
    }
}