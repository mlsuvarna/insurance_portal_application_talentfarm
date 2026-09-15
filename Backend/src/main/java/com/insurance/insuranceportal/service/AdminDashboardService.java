package com.insurance.insuranceportal.service;

import com.insurance.insuranceportal.dto.AdminDashboardResponse;
import com.insurance.insuranceportal.repository.ClaimRepository;
import com.insurance.insuranceportal.repository.PolicyRepository;
import com.insurance.insuranceportal.repository.PolicyTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final PolicyTypeRepository
            policyTypeRepository;

    private final PolicyRepository
            policyRepository;

    private final ClaimRepository
            claimRepository;

    public AdminDashboardService(
            PolicyTypeRepository policyTypeRepository,
            PolicyRepository policyRepository,
            ClaimRepository claimRepository
    ) {
        this.policyTypeRepository =
                policyTypeRepository;

        this.policyRepository =
                policyRepository;

        this.claimRepository =
                claimRepository;
    }

    public AdminDashboardResponse getSummary() {

        long policyTypeCount =
                policyTypeRepository.count();

        long policyCount =
                policyRepository.count();

        long claimCount =
                claimRepository.count();

        return new AdminDashboardResponse(
                policyTypeCount,
                policyCount,
                claimCount
        );
    }
}