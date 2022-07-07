package com.deingun.bankingsystem.service.interfaces;

import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.model.user.ThirdParty;
import com.deingun.bankingsystem.model.user.User;
import com.deingun.bankingsystem.utils.Address;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> findAll();

    AccountHolder findById(Long id);

    List<AccountHolder> findAllAccountHolders();

    AccountHolder createAccountHolder(String username, String password, String name, String nif, LocalDate dateOfBirth, String street, String city, String country, Integer postalCode, String mailingAddress);

    ThirdParty createThirdParty(String username, String password, String name, String hashedKey);

}
