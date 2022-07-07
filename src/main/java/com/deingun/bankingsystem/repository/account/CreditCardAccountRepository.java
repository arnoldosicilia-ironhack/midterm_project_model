package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.model.account.CreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Long> {

    Optional<CreditCardAccount> findById(Long id);

    List<CreditCardAccount> findByPrimaryOwnerId(Long id);

    List<CreditCardAccount> findAll();
}
