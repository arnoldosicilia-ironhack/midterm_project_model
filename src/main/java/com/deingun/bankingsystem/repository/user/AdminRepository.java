package com.deingun.bankingsystem.repository.user;

import com.deingun.bankingsystem.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findById(Long id);

    Optional<Admin> findByName(String name);

    List<Admin> findAll();

    Optional<Admin> findByUsername(String username);
}
