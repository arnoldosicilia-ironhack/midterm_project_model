package com.deingun.bankingsystem.model.account;

import com.deingun.bankingsystem.enums.AccountType;
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
@Table(name = "credit_card_account")
public class CreditCardAccount extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
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


    public CreditCardAccount() {
    }

    public CreditCardAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, Float interestRate, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default creditLimit 100
     **/
    public CreditCardAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Float interestRate, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.creditLimit = new BigDecimal("100").setScale(3, RoundingMode.HALF_EVEN);
        this.interestRate = interestRate;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default interestRate 0.2
     **/
    public CreditCardAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, AccountType accountType, LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.creditLimit = creditLimit;
        this.interestRate = 0.2F;
        this.lastInterestRateDate = lastInterestRateDate;
    }

    /**
     * Class constructor using default creditLimit 100, interestRate 0.2
     **/
    public CreditCardAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, AccountType accountType,LocalDate lastInterestRateDate) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.creditLimit = new BigDecimal("100").setScale(3, RoundingMode.HALF_EVEN);
        this.interestRate = 0.2F;
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

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
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
