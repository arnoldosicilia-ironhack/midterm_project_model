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
@Table(name = "saving_account")
public class SavingAccount extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "secret_key", nullable = false)
    private String secretKey;
    @Column(name = "minimum_balance")
    private BigDecimal minimumBalance;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "interest_rate")
    private Float interestRate;
    @Column(name = "last_interest_rate_date")
    private LocalDate lastInterestRateDate;

    @OneToMany(mappedBy = "originAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsOriginated;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsreceived;

    public SavingAccount() {
    }

    public SavingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minimumBalance, LocalDate creationDate, Status status, Float interestRate, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.creationDate = creationDate;
        this.status = status;
        this.interestRate = interestRate;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default interestRate 0.0025
     **/
    public SavingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minimumBalance, LocalDate creationDate, Status status, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.creationDate = creationDate;
        this.status = status;
        this.interestRate = 0.0025F;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default minimumBalance 1000
     **/
    public SavingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status, Float interestRate, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal("1000").setScale(3, RoundingMode.HALF_EVEN);
        this.creationDate = creationDate;
        this.status = status;
        this.interestRate = interestRate;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default minimumBalance 1000, default interestRate 0.0025
     **/
    public SavingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal("1000").setScale(3, RoundingMode.HALF_EVEN);
        this.creationDate = creationDate;
        this.status = status;
        this.interestRate = 0.0025F;
        this.lastInterestRateDate = lastInterestRateDate;
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
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
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

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestRateDate() {
        return lastInterestRateDate;
    }

    public void setLastInterestRateDate(LocalDate lastInterestRateDate) {
        this.lastInterestRateDate = lastInterestRateDate;
    }
}
