package com.deingun.bankingsystem.repository.user;

import com.deingun.bankingsystem.model.user.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    Optional<ThirdParty> findById(Long id);

    Optional<ThirdParty> findByName(String name);

    List<ThirdParty> findAll();
}
