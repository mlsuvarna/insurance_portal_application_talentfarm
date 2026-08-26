package com.insurance.insuranceportal.repository;

import com.insurance.insuranceportal.entity.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PolicyTypeRepository
        extends JpaRepository<PolicyType, Long> {

    Optional<PolicyType> findByTypeName(String typeName);

    boolean existsByTypeName(String typeName);

    List<PolicyType> findByActiveTrue();
}