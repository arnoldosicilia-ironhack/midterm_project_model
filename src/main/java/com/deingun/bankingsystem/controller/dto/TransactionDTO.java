package com.deingun.bankingsystem.controller.dto;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionDTO {

    @NotEmpty(message = "Origin account must be provided")
    private String originAccount;
    @NotEmpty(message = "Destination account must be provided")
    private String destinationAccount;
    @NotNull(message = "Amount must be provided")
    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    private BigDecimal amount;


    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}

