package com.example.subscribly.service;

import com.example.subscribly.dto.SubscriptionRequest;
import com.example.subscribly.model.*;
import com.example.subscribly.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TenantService tenantService;
    private final PlanService planService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               TenantService tenantService,
                               PlanService planService) {
        this.subscriptionRepository = subscriptionRepository;
        this.tenantService = tenantService;
        this.planService = planService;
    }

    public Subscription createSubscription(SubscriptionRequest request) {
        Tenant tenant = tenantService.getTenant(request.getTenantId());
        Plan plan = planService.getPlan(request.getPlanId());

        Subscription subscription = new Subscription();
        subscription.setTenant(tenant);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        LocalDate today = LocalDate.now();
        subscription.setCurrentPeriodStart(today);

        if (plan.getBillingPeriod() == BillingPeriod.YEARLY) {
            subscription.setCurrentPeriodEnd(today.plusYears(1));
        } else {
            subscription.setCurrentPeriodEnd(today.plusMonths(1));
        }

        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptionsForTenant(Long tenantId) {
        return subscriptionRepository.findByTenantId(tenantId);
    }

    public List<Subscription> findDueSubscriptions(LocalDate today) {
        return subscriptionRepository.findByStatusAndCurrentPeriodEndLessThanEqual(
                SubscriptionStatus.ACTIVE, today);
    }

    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + id));
    }
}
