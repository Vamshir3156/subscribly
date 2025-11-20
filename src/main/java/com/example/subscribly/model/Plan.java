package com.example.subscribly.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private BillingPeriod billingPeriod;

    @Column(nullable = false)
    private BigDecimal pricePerPeriod;

    private Integer maxUsers;

    @Column(nullable = false)
    private Long apiCallLimit;

    @Column(nullable = false)
    private BigDecimal extraApiCallPricePerThousand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public BigDecimal getPricePerPeriod() {
        return pricePerPeriod;
    }

    public void setPricePerPeriod(BigDecimal pricePerPeriod) {
        this.pricePerPeriod = pricePerPeriod;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Long getApiCallLimit() {
        return apiCallLimit;
    }

    public void setApiCallLimit(Long apiCallLimit) {
        this.apiCallLimit = apiCallLimit;
    }

    public BigDecimal getExtraApiCallPricePerThousand() {
        return extraApiCallPricePerThousand;
    }

    public void setExtraApiCallPricePerThousand(BigDecimal extraApiCallPricePerThousand) {
        this.extraApiCallPricePerThousand = extraApiCallPricePerThousand;
    }
}
