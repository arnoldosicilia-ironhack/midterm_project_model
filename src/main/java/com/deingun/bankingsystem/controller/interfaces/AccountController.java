package com.deingun.bankingsystem.controller.interfaces;

import com.deingun.bankingsystem.controller.dto.CheckingAccountDTO;
import com.deingun.bankingsystem.controller.dto.CreditCardAccountDTO;
import com.deingun.bankingsystem.controller.dto.SavingAccountDTO;
import com.deingun.bankingsystem.controller.dto.TransactionDTO;
import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.security.CustomUserDetails;

import java.util.List;

public interface AccountController {

    List<Account> findAllAccounts(CustomUserDetails customUserDetails);

    String getAccountBalance(String accountNumber, CustomUserDetails customUserDetails);

    Account createCheckingAccount(CheckingAccountDTO checkingAccountDTO);

    Account createSavingAccount(SavingAccountDTO savingAccountDTO);

    Account createCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);

    void updateBalance(String accountNumber, TransactionDTO transactionDTO);

    void updateStatus(String accountNumber);
}
