package com.deingun.bankingsystem.repository.user;

import com.deingun.bankingsystem.model.user.*;
import com.deingun.bankingsystem.utils.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountHolderRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Autowired
    AccountHolderRepository accountHolderRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;


    @BeforeEach
    void setUp() {

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));

    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findById_validId_isPresent() {
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(accountHolderTest1.getId());
        assertTrue(optionalAccountHolder.isPresent());
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<AccountHolder> optionalUser = accountHolderRepository.findById(-1L);
        assertTrue(optionalUser.isEmpty());
    }


    @Test
    void findAll_noParams_AccountHoldersList() {
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertEquals(2, accountHolderList.size());
        assertEquals("accountHolderTest1", accountHolderList.get(0).getUsername());
        assertEquals("accountHolderTest2", accountHolderList.get(1).getUsername());
    }

    @Test
    void findByName_validName_isPresent() {
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByName("NameTest1");
        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("11111111A", optionalAccountHolder.get().getNif());
    }

    @Test
    void findByName_invalidName_isEmpty() {
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByName("invalidName");
        assertTrue(optionalAccountHolder.isEmpty());
    }

    @Test
    void findByNif_validNif_isPresent() {
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByNif("11111111A");
        assertTrue(optionalAccountHolder.isPresent());
        assertEquals("NameTest1", optionalAccountHolder.get().getName());
    }

    @Test
    void findByNif_invalidNif_isEmpty() {
        Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findByNif("AAAAAAAAAA");
        assertTrue(optionalAccountHolder.isEmpty());
    }

}