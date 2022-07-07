package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.model.account.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {

    Optional<SavingAccount> findById(Long id);

    List<SavingAccount> findByPrimaryOwnerId(Long id);

    List<SavingAccount> findAll();

    Optional<Account> findByAccountNumber(String accountNumber);
}
