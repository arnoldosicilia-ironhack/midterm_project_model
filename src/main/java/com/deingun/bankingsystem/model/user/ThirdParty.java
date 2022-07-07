package com.deingun.bankingsystem.model.user;

import com.deingun.bankingsystem.enums.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "third_party")
public class ThirdParty extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Column(name = "hashed_key", nullable = false)
    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String username, String password, LocalDate passwordDate, String name, String hashedKey) {
        super(username, password, passwordDate, Role.THIRDPARTY);
        this.name = name;
        this.hashedKey = hashedKey;
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
