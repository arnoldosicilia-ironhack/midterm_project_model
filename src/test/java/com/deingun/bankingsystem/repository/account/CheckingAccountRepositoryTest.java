package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.Account;
import com.deingun.bankingsystem.model.account.CheckingAccount;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.repository.user.AccountHolderRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import com.deingun.bankingsystem.utils.Address;
import com.deingun.bankingsystem.utils.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckingAccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    CheckingAccount checkingAccountTest1;
    CheckingAccount checkingAccountTest2;


    @BeforeEach
    void setUp() {

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));


        checkingAccountTest1 = new CheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());
        checkingAccountTest2 = new CheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.CHECKING,LocalDate.now());

        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));
        checkingAccountTest1.setAccountNumber(checkingAccountTest1.getEntityNumber() + checkingAccountTest1.getBranchNumber() + checkingAccountTest1.getId().toString());
        checkingAccountTest2.setAccountNumber(checkingAccountTest2.getEntityNumber() + checkingAccountTest2.getBranchNumber() + checkingAccountTest2.getId().toString());
        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));
    }

    @AfterEach
    void tearDown() {
        checkingAccountRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();

    }

    @Test
    void findById_validId_isPresent() {
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(checkingAccountTest1.getId());
        assertTrue(optionalCheckingAccount.isPresent());
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(-1L);
        assertTrue(optionalCheckingAccount.isEmpty());
    }


    @Test
    void findByPrimaryOwnerId_validId_isPresent() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findByPrimaryOwnerId(accountHolderTest1.getId());
        assertEquals(1, checkingAccountList.size());
        Assertions.assertThat(checkingAccountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
        Assertions.assertThat(checkingAccountList.get(0).getMinimumBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(250));
        Assertions.assertThat(checkingAccountList.get(0).getMonthlyMaintenanceFee())
                .isEqualByComparingTo(BigDecimal.valueOf(12));
    }

    @Test
    void findAll_noParams_AccountList() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findAll();
        assertEquals(2, checkingAccountList.size());
    }
}