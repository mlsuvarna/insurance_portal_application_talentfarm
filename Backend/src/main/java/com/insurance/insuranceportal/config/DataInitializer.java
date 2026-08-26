package com.insurance.insuranceportal.config;

import com.insurance.insuranceportal.entity.Role;
import com.insurance.insuranceportal.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String[] args) {

        createRoleIfNotExists(
                "CUSTOMER",
                "Customer self-service portal access"
        );

        createRoleIfNotExists(
                "AGENT",
                "Insurance agent access"
        );

        createRoleIfNotExists(
                "CLAIMS_OFFICER",
                "Claims review and decision access"
        );

        createRoleIfNotExists(
                "ADMIN",
                "Administrator access"
        );
    }

    private void createRoleIfNotExists(
            String roleName,
            String description
    ) {

        if (roleRepository
                .findByRoleName(roleName)
                .isEmpty()) {

            Role role = new Role(
                    roleName,
                    description
            );

            roleRepository.save(role);
        }
    }
}