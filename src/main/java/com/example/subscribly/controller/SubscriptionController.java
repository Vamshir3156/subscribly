package com.example.subscribly.controller;

import com.example.subscribly.dto.SubscriptionRequest;
import com.example.subscribly.model.Subscription;
import com.example.subscribly.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        Subscription subscription = subscriptionService.createSubscription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    @GetMapping
    public List<Subscription> listSubscriptions(@RequestParam Long tenantId) {
        return subscriptionService.getSubscriptionsForTenant(tenantId);
    }
}
