package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.AdminDashboardResponse;
import com.insurance.insuranceportal.service.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService
            adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService
    ) {
        this.adminDashboardService =
                adminDashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<AdminDashboardResponse>
    getSummary() {

        AdminDashboardResponse response =
                adminDashboardService.getSummary();

        return ResponseEntity.ok(response);
    }
}