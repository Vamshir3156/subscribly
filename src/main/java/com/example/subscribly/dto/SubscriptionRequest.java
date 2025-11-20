package com.example.subscribly.dto;

import jakarta.validation.constraints.NotNull;

public class SubscriptionRequest {

    @NotNull
    private Long tenantId;

    @NotNull
    private Long planId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}
