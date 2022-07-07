package com.deingun.bankingsystem.model.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.Transaction;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.utils.Money;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
@Table(name = "student_checking_account")
public class StudentCheckingAccount extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "secret_key", nullable = false)
    @NotEmpty(message = "Secret Key must be provided")
    private String secretKey;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "originAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsOriginated;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsreceived;

    public StudentCheckingAccount() {
    }

    public StudentCheckingAccount(String entityNumber, String branchNumber, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, LocalDate creationDate, Status status, AccountType accountType) {
        super(entityNumber, branchNumber, balance, primaryOwner,secondaryOwner, accountType);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
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
}
