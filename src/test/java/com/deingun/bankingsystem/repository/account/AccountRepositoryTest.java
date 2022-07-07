package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.*;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    CheckingAccount checkingAccountTest1;
    CheckingAccount checkingAccountTest2;
    CreditCardAccount creditCardAccountTest1;
    CreditCardAccount creditCardAccountTest2;
    SavingAccount savingAccountTest1;
    SavingAccount savingAccountTest2;
    StudentCheckingAccount studentCheckingAccountTest1;
    StudentCheckingAccount studentCheckingAccountTest2;

    @BeforeEach
    void setUp() {
        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));
        BigDecimal minimumBalance = new BigDecimal("1000").setScale(3, RoundingMode.HALF_EVEN);

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));


        checkingAccountTest1 = new CheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());
        checkingAccountTest2 = new CheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());

        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));
        checkingAccountTest1.setAccountNumber(checkingAccountTest1.getEntityNumber() + checkingAccountTest1.getBranchNumber() + checkingAccountTest1.getId().toString());
        checkingAccountTest2.setAccountNumber(checkingAccountTest2.getEntityNumber() + checkingAccountTest2.getBranchNumber() + checkingAccountTest2.getId().toString());
        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));

        creditCardAccountTest1 = new CreditCardAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2,AccountType.CREDIT_CARD, LocalDate.now());
        creditCardAccountTest2 = new CreditCardAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, new BigDecimal("500"), 0.15F, AccountType.CREDIT_CARD,LocalDate.now());


        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2));
        creditCardAccountTest1.setAccountNumber(creditCardAccountTest1.getEntityNumber() + creditCardAccountTest1.getBranchNumber() + creditCardAccountTest1.getId().toString());
        creditCardAccountTest2.setAccountNumber(creditCardAccountTest2.getEntityNumber() + creditCardAccountTest2.getBranchNumber() + creditCardAccountTest2.getId().toString());
        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2));

        savingAccountTest1 = new SavingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc", minimumBalance, LocalDate.now(), Status.ACTIVE, 0.0050F, AccountType.SAVING, LocalDate.now());
        savingAccountTest2 = new SavingAccount("0049", "1500", balance, accountHolderTest2, accountHolderTest1, "123abc", minimumBalance, LocalDate.now(), Status.ACTIVE,AccountType.SAVING, LocalDate.now());


        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2));
        savingAccountTest1.setAccountNumber(savingAccountTest1.getEntityNumber() + savingAccountTest1.getBranchNumber() + savingAccountTest1.getId().toString());
        savingAccountTest2.setAccountNumber(savingAccountTest2.getEntityNumber() + savingAccountTest2.getBranchNumber() + savingAccountTest2.getId().toString());
        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2));

        studentCheckingAccountTest1 = new StudentCheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.STUDENT_CHECKING);
        studentCheckingAccountTest2 = new StudentCheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.STUDENT_CHECKING);

        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));
        studentCheckingAccountTest1.setAccountNumber(studentCheckingAccountTest1.getEntityNumber() + studentCheckingAccountTest1.getBranchNumber() + studentCheckingAccountTest1.getId().toString());
        studentCheckingAccountTest2.setAccountNumber(studentCheckingAccountTest2.getEntityNumber() + studentCheckingAccountTest2.getBranchNumber() + studentCheckingAccountTest2.getId().toString());
        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));

    }

    @AfterEach
    void tearDown() {

        checkingAccountRepository.deleteAll();
        creditCardAccountRepository.deleteAll();
        savingAccountRepository.deleteAll();
        studentCheckingAccountRepository.deleteAll();
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void deleteInBatch() {
    }

    @Test
    void findById_validId_isPresent() {
        Optional<Account> optionalAccount = accountRepository.findById(checkingAccountTest1.getId());
        assertTrue(optionalAccount.isPresent());
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<Account> optionalAccount = accountRepository.findById(-1L);
        assertTrue(optionalAccount.isEmpty());
    }

    @Test
    void findByPrimaryOwnerId_validId_isPresent() {
        List<Account> accountList = accountRepository.findByPrimaryOwnerId(accountHolderTest1.getId());
        assertEquals(4, accountList.size());
        Assertions.assertThat(accountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void findBySecondaryOwnerId_validId_isPresent() {
        List<Account> accountList = accountRepository.findBySecondaryOwnerId(accountHolderTest1.getId());
        assertEquals(4, accountList.size());
        Assertions.assertThat(accountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void findByPrimaryOwnerIdOrBySecondaryOwnerId_validId_isPresent() {
        List<Account> accountList = accountRepository.findByPrimaryOwnerIdOrSecondaryOwnerId(accountHolderTest1.getId(),accountHolderTest1.getId());
        assertEquals(8, accountList.size());

    }

    @Test
    void findAll_noParams_AccountList() {
        List<Account> accountList = accountRepository.findAll();
        assertEquals(8, accountList.size());
    }
}