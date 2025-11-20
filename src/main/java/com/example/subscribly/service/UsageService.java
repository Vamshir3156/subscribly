package com.example.subscribly.service;

import com.example.subscribly.dto.UsageRequest;
import com.example.subscribly.model.Subscription;
import com.example.subscribly.model.UsageRecord;
import com.example.subscribly.repository.UsageRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class UsageService {

    private final UsageRecordRepository usageRecordRepository;
    private final SubscriptionService subscriptionService;

    public UsageService(UsageRecordRepository usageRecordRepository,
                        SubscriptionService subscriptionService) {
        this.usageRecordRepository = usageRecordRepository;
        this.subscriptionService = subscriptionService;
    }

    public UsageRecord recordUsage(UsageRequest request) {
        Subscription subscription = subscriptionService.getById(request.getSubscriptionId());
        UsageRecord record = new UsageRecord();
        record.setSubscription(subscription);
        record.setMetricType(request.getMetricType());
        record.setQuantity(request.getQuantity());
        return usageRecordRepository.save(record);
    }

    public long getUsageForSubscriptionAndPeriod(Subscription subscription,
                                                 String metricType,
                                                 LocalDate periodStart,
                                                 LocalDate periodEnd) {
        LocalDateTime start = periodStart.atStartOfDay();
        LocalDateTime end = periodEnd.atTime(LocalTime.MAX);
        List<UsageRecord> records = usageRecordRepository
                .findBySubscriptionAndMetricTypeAndTimestampBetween(
                        subscription, metricType, start, end);

        return records.stream()
                .mapToLong(UsageRecord::getQuantity)
                .sum();
    }
}
