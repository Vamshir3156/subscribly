package com.example.subscribly.repository;

import com.example.subscribly.model.Subscription;
import com.example.subscribly.model.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByTenantId(Long tenantId);

    List<Subscription> findByStatusAndCurrentPeriodEndLessThanEqual(SubscriptionStatus status, LocalDate date);
}
