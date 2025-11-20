package com.example.subscribly.service;

import com.example.subscribly.model.*;
import com.example.subscribly.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BillingService {

    private final SubscriptionService subscriptionService;
    private final UsageService usageService;
    private final InvoiceRepository invoiceRepository;

    public BillingService(SubscriptionService subscriptionService,
                          UsageService usageService,
                          InvoiceRepository invoiceRepository) {
        this.subscriptionService = subscriptionService;
        this.usageService = usageService;
        this.invoiceRepository = invoiceRepository;
    }

    public int runBilling() {
        LocalDate today = LocalDate.now();
        List<Subscription> dueSubscriptions = subscriptionService.findDueSubscriptions(today);
        int invoicesCreated = 0;

        for (Subscription subscription : dueSubscriptions) {
            Plan plan = subscription.getPlan();
            Tenant tenant = subscription.getTenant();

            LocalDate periodStart = subscription.getCurrentPeriodStart();
            LocalDate periodEnd = subscription.getCurrentPeriodEnd();

            BigDecimal baseAmount = plan.getPricePerPeriod();

            long totalUsage = usageService.getUsageForSubscriptionAndPeriod(
                    subscription, "API_CALL", periodStart, periodEnd);

            long included = plan.getApiCallLimit();
            long extra = Math.max(0, totalUsage - included);

            BigDecimal usageAmount = BigDecimal.ZERO;
            if (extra > 0) {
                long thousands = (extra + 999) / 1000;
                usageAmount = plan.getExtraApiCallPricePerThousand()
                        .multiply(BigDecimal.valueOf(thousands));
            }

            BigDecimal amountDue = baseAmount.add(usageAmount).setScale(2, RoundingMode.HALF_UP);

            Invoice invoice = new Invoice();
            invoice.setTenant(tenant);
            invoice.setSubscription(subscription);
            invoice.setBaseAmount(baseAmount);
            invoice.setUsageAmount(usageAmount);
            invoice.setAmountDue(amountDue);
            invoice.setCurrency("USD");
            invoice.setPeriodStart(periodStart);
            invoice.setPeriodEnd(periodEnd);
            invoice.setStatus(InvoiceStatus.PENDING);

            invoiceRepository.save(invoice);
            invoicesCreated++;

            LocalDate newStart = periodEnd.plusDays(1);
            subscription.setCurrentPeriodStart(newStart);
            if (plan.getBillingPeriod() == BillingPeriod.YEARLY) {
                subscription.setCurrentPeriodEnd(newStart.plusYears(1));
            } else {
                subscription.setCurrentPeriodEnd(newStart.plusMonths(1));
            }

            subscriptionService.save(subscription);
        }

        return invoicesCreated;
    }
}
