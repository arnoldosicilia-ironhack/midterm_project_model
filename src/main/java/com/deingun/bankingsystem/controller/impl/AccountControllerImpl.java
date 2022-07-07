package com.deingun.bankingsystem.controller.impl;

import com.deingun.bankingsystem.controller.dto.CheckingAccountDTO;
import com.deingun.bankingsystem.controller.dto.CreditCardAccountDTO;
import com.deingun.bankingsystem.controller.dto.SavingAccountDTO;
import com.deingun.bankingsystem.controller.dto.TransactionDTO;
import com.deingun.bankingsystem.controller.interfaces.AccountController;
import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.security.CustomUserDetails;
import com.deingun.bankingsystem.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    AccountService accountService;


    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAllAccounts(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return accountService.findAllAccounts(customUserDetails);
    }

    @GetMapping("/accounts/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public String getAccountBalance(@PathVariable(name = "accountNumber") String accountNumber,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return accountService.getAccountBalance(accountNumber,customUserDetails);
    }

    @PatchMapping("/accounts/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "accountNumber") String accountNumber, @RequestBody TransactionDTO transactionDTO) {
        accountService.updateBalance(accountNumber, transactionDTO.getAmount());
    }

    @PatchMapping("/status/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable(name = "accountNumber") String accountNumber) {
        accountService.updateStatus(accountNumber);
    }

    @PostMapping("/checkingaccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody @Valid CheckingAccountDTO checkingAccountDTO) {
        return accountService.createCheckingAccount(checkingAccountDTO.getEntityNumber(), checkingAccountDTO.getBranchNumber(),
                checkingAccountDTO.getAmount(), checkingAccountDTO.getPrimaryOwnerId(),
                checkingAccountDTO.getSecondaryOwnerId(), checkingAccountDTO.getSecretKey());
    }

    @PostMapping("/savingaccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingAccount(@RequestBody @Valid SavingAccountDTO savingAccountDTO) {
        return accountService.createSavingAccount(savingAccountDTO.getEntityNumber(), savingAccountDTO.getBranchNumber(), savingAccountDTO.getAmount(),
                savingAccountDTO.getPrimaryOwnerId(), savingAccountDTO.getSecondaryOwnerId(), savingAccountDTO.getSecretKey(),
                savingAccountDTO.getMinimumBalance(), savingAccountDTO.getInterestRate());
    }

    @PostMapping("/creditcardaccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditCardAccount(@RequestBody @Valid CreditCardAccountDTO creditCardAccountDTO) {

        return accountService.createCreditCardAccount(creditCardAccountDTO.getEntityNumber(), creditCardAccountDTO.getBranchNumber(), creditCardAccountDTO.getAmount(),
                creditCardAccountDTO.getPrimaryOwnerId(), creditCardAccountDTO.getSecondaryOwnerId(), creditCardAccountDTO.getCreditLimit(),
                creditCardAccountDTO.getInterestRate());
    }
}
