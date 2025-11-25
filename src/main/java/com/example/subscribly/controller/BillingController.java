package com.example.subscribly.controller;

import com.example.subscribly.dto.BillingRunResponse;
import com.example.subscribly.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/run")
    public ResponseEntity<BillingRunResponse> runBilling() {
        int invoicesCreated = billingService.runBilling();
        return ResponseEntity.ok(new BillingRunResponse(invoicesCreated));
    }
}
