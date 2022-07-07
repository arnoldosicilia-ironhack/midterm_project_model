package com.deingun.bankingsystem.model.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.Transaction;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.utils.Money;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
@Table(name = "checking_account")
public class CheckingAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "secret_key", nullable = false)
    private String secretKey;
    @Column(name = "minimum_balance")
    private final BigDecimal MINIMUMBALANCE = new BigDecimal("250").setScale(3, RoundingMode.HALF_EVEN);
    @Column(name = "monthly_maintenance_fee")
    private final BigDecimal MONTHLYMAINTENANCEFEE = new BigDecimal("12").setScale(3, RoundingMode.HALF_EVEN);
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "last_monthly_fee_date")
    private LocalDate lastMonthlyFeeDate;

    @OneToMany(mappedBy = "originAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsOriginated;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsreceived;

    public CheckingAccount() {
    }

    public CheckingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status, AccountType accountType, LocalDate lastMonthlyFeeDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
        this.lastMonthlyFeeDate = lastMonthlyFeeDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return MINIMUMBALANCE;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return MONTHLYMAINTENANCEFEE;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getLastMonthlyFeeDate() {
        return lastMonthlyFeeDate;
    }

    public void setLastMonthlyFeeDate(LocalDate lastMonthlyFeeDate) {
        this.lastMonthlyFeeDate = lastMonthlyFeeDate;
    }
}
