package com.deingun.bankingsystem.controller.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AccountHolderDTO {
    private Long id;
    @NotEmpty(message = "Username must be provided")
    private String username;
    @NotEmpty(message = "Password must be provided")
    @Size(min = 6, message = "The password must contain at least 6 characters")
    private String password;
    @NotEmpty(message = "Name must be provided")
    private String name;
    @NotEmpty(message = "Nif must be provided")
    private String nif;
    @NotNull(message = "Date of birth must be provided")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "Street must be provided")
    private String street;
    @NotEmpty(message = "City must be provided")
    private String city;
    @NotEmpty(message = "Country must be provided")
    private String country;
    @NotNull(message = "Postal Code must be provided")
    private Integer postalCode;
    @Email(message = "Must be a properly formatted email address")
    private String mailingAddress;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
