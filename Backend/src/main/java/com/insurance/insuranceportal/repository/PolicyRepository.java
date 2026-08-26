package com.insurance.insuranceportal.repository;

import com.insurance.insuranceportal.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository
        extends JpaRepository<Policy, Long> {

    Optional<Policy> findByPolicyNumber(
            String policyNumber
    );

    boolean existsByPolicyNumber(
            String policyNumber
    );

    List<Policy> findByUserUserId(
            Long userId
    );

    List<Policy> findByUserUserIdAndStatus(
            Long userId,
            String status
    );
}