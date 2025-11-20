package com.example.subscribly.dto;

public class BillingRunResponse {

    private int invoicesCreated;

    public BillingRunResponse(int invoicesCreated) {
        this.invoicesCreated = invoicesCreated;
    }

    public int getInvoicesCreated() {
        return invoicesCreated;
    }

    public void setInvoicesCreated(int invoicesCreated) {
        this.invoicesCreated = invoicesCreated;
    }
}
