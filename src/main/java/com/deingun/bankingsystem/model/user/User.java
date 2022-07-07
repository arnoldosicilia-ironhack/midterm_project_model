package com.deingun.bankingsystem.model.user;

import com.deingun.bankingsystem.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "username", nullable = false, length = 64, unique = true)
    @Size(max = 64)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "password_date", nullable = false)
    private LocalDate passwordDate;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    public User() {
    }

    public User(String username, String password, LocalDate passwordDate, Role role) {
        this.username = username;
        this.password = password;
        this.passwordDate = passwordDate;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getPasswordDate() {
        return passwordDate;
    }

    public void setPasswordDate(LocalDate passwordDate) {
        this.passwordDate = passwordDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRoleSet(Role role) {
        this.role = role;
    }
}

