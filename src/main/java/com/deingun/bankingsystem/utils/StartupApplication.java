package com.deingun.bankingsystem.utils;

import com.deingun.bankingsystem.model.user.Admin;
import com.deingun.bankingsystem.model.user.User;
import com.deingun.bankingsystem.repository.user.AdminRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class StartupApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * method to create an admin test user when the application starts
     */
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        Optional<User> optionalUser = userRepository.findByUsername("adminTest");
        if (optionalUser.isEmpty()) {
            Admin adminTest1 = new Admin("adminTest", passwordEncoder.encode("123456"), LocalDate.now(), "admin");
            adminRepository.save(adminTest1);
        }
    }
}

