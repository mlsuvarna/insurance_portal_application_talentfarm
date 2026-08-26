package com.insurance.insuranceportal.repository;

import com.insurance.insuranceportal.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository
        extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNumber(
            String claimNumber
    );

    boolean existsByClaimNumber(
            String claimNumber
    );

    List<Claim> findByPolicyPolicyId(
            Long policyId
    );

    List<Claim> findByPolicyUserUserId(
            Long userId
    );

    List<Claim> findByStatus(
            String status
    );

    List<Claim> findByPolicyUserUserIdAndStatus(
            Long userId,
            String status
    );
}