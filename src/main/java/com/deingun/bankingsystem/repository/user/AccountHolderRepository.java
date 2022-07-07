package com.deingun.bankingsystem.repository.user;

import com.deingun.bankingsystem.model.user.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    Optional<AccountHolder> findById(Long id);

    Optional<AccountHolder> findByName(String name);

    Optional<AccountHolder> findByNif(String nif);

    List<AccountHolder> findAll();

}
