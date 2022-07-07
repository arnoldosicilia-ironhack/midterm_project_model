package com.deingun.bankingsystem.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckingAccountDTO {

    @NotEmpty(message = "Entity must be provided")
    @Length(min = 4,max = 4, message = "Entity number must contain 4 digits")
    private String entityNumber;
    @NotEmpty(message = "Branch must be provided")
    @Length(min = 4,max = 4, message = "Entity number must contain 4 digits")
    private String branchNumber;
    @NotNull(message = "Amount must be provided")
    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    private BigDecimal amount;
    private String currency;
    @NotNull(message = "Primary Owner must be provided")
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @NotEmpty(message = "Secret Key must be provided")
    private String secretKey;

    public String getEntityNumber() {
        return entityNumber;
    }

    public void setEntityNumber(String entityNumber) {
        this.entityNumber = entityNumber;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public BigDecimal getAmount() {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
