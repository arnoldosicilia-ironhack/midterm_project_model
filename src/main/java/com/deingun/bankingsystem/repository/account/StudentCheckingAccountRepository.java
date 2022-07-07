package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.model.account.StudentCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCheckingAccountRepository extends JpaRepository<StudentCheckingAccount, Long> {

    Optional<StudentCheckingAccount> findById(Long id);

    List<StudentCheckingAccount> findByPrimaryOwnerId(Long id);

    List<StudentCheckingAccount> findAll();

    Optional<Account> findByAccountNumber(String accountNumber);
}
