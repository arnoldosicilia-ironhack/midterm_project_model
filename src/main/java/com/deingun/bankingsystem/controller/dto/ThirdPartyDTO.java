package com.deingun.bankingsystem.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ThirdPartyDTO {
    private Long id;
    @NotEmpty(message = "Username must be provided")
    private String username;
    @NotEmpty(message = "Password must be provided")
    @Size(min = 6, message = "The password must contain at least 6 characters")
    private String password;
    @NotEmpty(message = "Name must be provided")
    private String name;
    @NotEmpty(message = "Hashed Key must be provided")
    @Size(min = 6)
    private String hashedKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
