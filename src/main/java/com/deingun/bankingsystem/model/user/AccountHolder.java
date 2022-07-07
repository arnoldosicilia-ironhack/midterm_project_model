package com.deingun.bankingsystem.model.user;

import com.deingun.bankingsystem.enums.Role;
import com.deingun.bankingsystem.model.Transaction;
import com.deingun.bankingsystem.model.account.*;
import com.deingun.bankingsystem.utils.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "account_holder")
public class AccountHolder extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Column(name = "nif", nullable = false, length = 10)
    private String nif;
    @Column(name = "date_of_birth", nullable = false)

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Account> AccountsAsPrimaryOwner;

    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Account> AccountsAsSecondaryOwner;

    @OneToMany(mappedBy = "paymaster", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsAsPaymaster;

    @OneToMany(mappedBy = "destinationAccount", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Transaction> transactionsAsReceiver;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    })
    private Address address;
    @Column(name = "mailing_address")
    private String mailingAddress;

    public AccountHolder() {
    }

    public AccountHolder(String username, String password, LocalDate passwordDate, String name, String nif, LocalDate dateOfBirth, Address address, String mailingAddress) {
        super(username, password, passwordDate, Role.ACCOUNTHOLDER);
        this.name = name;
        this.nif = nif;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

//    public List<Account> getAccountsAsPrimaryOwner() {
//        return AccountsAsPrimaryOwner;
//    }

    public void setAccountsAsPrimaryOwner(List<Account> accountsAsPrimaryOwner) {
        AccountsAsPrimaryOwner = accountsAsPrimaryOwner;
    }

//    public List<Account> getAccountsAsSecondaryOwner() {
//        return AccountsAsSecondaryOwner;
//    }

    public void setAccountsAsSecondaryOwner(List<Account> accountsAsSecondaryOwner) {
        AccountsAsSecondaryOwner = accountsAsSecondaryOwner;
    }

//    public List<Transaction> getTransactionsAsPaymaster() {
//        return transactionsAsPaymaster;
//    }

    public void setTransactionsAsPaymaster(List<Transaction> transactionsAsPaymaster) {
        this.transactionsAsPaymaster = transactionsAsPaymaster;
    }

//    public List<Transaction> getTransactionsAsReceiver() {
//        return transactionsAsReceiver;
//    }

    public void setTransactionsAsReceiver(List<Transaction> transactionsAsReceiver) {
        this.transactionsAsReceiver = transactionsAsReceiver;
    }
}
