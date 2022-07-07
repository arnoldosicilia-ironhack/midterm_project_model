package com.deingun.bankingsystem.model;

import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.model.user.ThirdParty;
import com.deingun.bankingsystem.model.user.User;
import com.deingun.bankingsystem.utils.Money;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private Account originAccount;
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;
    @ManyToOne
    @JoinColumn(name = "paymaster_id")
    private User paymaster;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency")),
    })
    private Money amount;
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    public Transaction() {
    }

    public Transaction(Account originAccount, Account destinationAccount, User paymaster, User receiver, Money amount, LocalDateTime timeStamp) {
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.paymaster = paymaster;
        this.receiver = receiver;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Transaction(ThirdParty thirdParty, Account account, Money amount, LocalDateTime timeStamp) {
        this.originAccount = null;
        this.destinationAccount = account;
        this.paymaster = thirdParty;
        this.receiver = account.getPrimaryOwner();
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Account getOriginAccount() {
//        return originAccount;
//    }

    public void setOriginAccount(Account originAccount) {
        this.originAccount = originAccount;
    }

//    public Account getDestinationAccount() {
//        return destinationAccount;
//    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

//    public User getPaymaster() {
//        return paymaster;
//    }

    public void setPaymaster(User paymaster) {
        this.paymaster = paymaster;
    }

//    public User getReceiver() {
//        return receiver;
//    }

    public void setReceiver(User receiverId) {
        this.receiver = receiverId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOriginAccountNumber() {
        return originAccount.getAccountNumber();
    }

    public String getDestinationAccountNumber() {
        return destinationAccount.getAccountNumber();
    }

    public String getPaymasterName() {
        return paymaster.getUsername();
    }

    public String getReceiverName() {
        return receiver.getUsername();
    }
}
