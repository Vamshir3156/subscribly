package com.example.subscribly.repository;

import com.example.subscribly.model.UsageRecord;
import com.example.subscribly.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {

    List<UsageRecord> findBySubscriptionAndMetricTypeAndTimestampBetween(
            Subscription subscription,
            String metricType,
            LocalDateTime start,
            LocalDateTime end
    );
}
