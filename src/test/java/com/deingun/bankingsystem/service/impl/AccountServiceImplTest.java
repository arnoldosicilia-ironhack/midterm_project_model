package com.deingun.bankingsystem.service.impl;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.*;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.repository.TransactionRepository;
import com.deingun.bankingsystem.repository.account.*;
import com.deingun.bankingsystem.repository.user.AccountHolderRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import com.deingun.bankingsystem.security.CustomUserDetails;
import com.deingun.bankingsystem.service.interfaces.AccountService;
import com.deingun.bankingsystem.utils.Address;
import com.deingun.bankingsystem.utils.Money;
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
class AccountServiceImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    TransactionServiceImpl transactionServiceImpl;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    AccountHolder accountHolderTest3;
    CheckingAccount checkingAccountTest1;
    CheckingAccount checkingAccountTest2;
    CheckingAccount checkingAccountTest3;
    CreditCardAccount creditCardAccountTest1;
    CreditCardAccount creditCardAccountTest2;
    SavingAccount savingAccountTest1;
    SavingAccount savingAccountTest2;
    SavingAccount savingAccountTest3;
    StudentCheckingAccount studentCheckingAccountTest1;
    StudentCheckingAccount studentCheckingAccountTest2;
    CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));
        BigDecimal minimumBalance = new BigDecimal("1000").setScale(3, RoundingMode.HALF_EVEN);

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.now().minusYears(41), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.now().minusYears(31), addressTest, "test@gmail.com");
        accountHolderTest3 = new AccountHolder("accountHolderTest3", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest3", "33333333F", LocalDate.now().minusYears(21), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2, accountHolderTest3));


        checkingAccountTest1 = new CheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now().minusMonths(1));
        checkingAccountTest2 = new CheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());
        checkingAccountTest3 = new CheckingAccount("0049", "2020", new Money(new BigDecimal("200")), accountHolderTest1,null, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now().minusMonths(2));


        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2,checkingAccountTest3));
        checkingAccountTest1.setAccountNumber(checkingAccountTest1.getEntityNumber() + checkingAccountTest1.getBranchNumber() + checkingAccountTest1.getId().toString());
        checkingAccountTest2.setAccountNumber(checkingAccountTest2.getEntityNumber() + checkingAccountTest2.getBranchNumber() + checkingAccountTest2.getId().toString());
        checkingAccountTest3.setAccountNumber(checkingAccountTest3.getEntityNumber() + checkingAccountTest3.getBranchNumber() + checkingAccountTest3.getId().toString());
        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2, checkingAccountTest3));

        creditCardAccountTest1 = new CreditCardAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, AccountType.CREDIT_CARD,LocalDate.now().minusMonths(3));
        creditCardAccountTest2 = new CreditCardAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, new BigDecimal("500"), 0.12F, AccountType.CREDIT_CARD,LocalDate.now().minusMonths(1));


        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2));
        creditCardAccountTest1.setAccountNumber(creditCardAccountTest1.getEntityNumber() + creditCardAccountTest1.getBranchNumber() + creditCardAccountTest1.getId().toString());
        creditCardAccountTest2.setAccountNumber(creditCardAccountTest2.getEntityNumber() + creditCardAccountTest2.getBranchNumber() + creditCardAccountTest2.getId().toString());
        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2));

        savingAccountTest1 = new SavingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc", minimumBalance, LocalDate.now(), Status.ACTIVE, 0.0050F, AccountType.SAVING, LocalDate.now());
        savingAccountTest2 = new SavingAccount("0049", "1500", balance, accountHolderTest2, accountHolderTest1, "123abc", minimumBalance, LocalDate.now().minusMonths(13), Status.ACTIVE, AccountType.SAVING, LocalDate.of(2020,8,20));
        savingAccountTest3 = new SavingAccount("0049", "1500", new Money(new BigDecimal("2000")), accountHolderTest2, accountHolderTest1, "123abc", minimumBalance, LocalDate.now().minusYears(6), Status.ACTIVE, AccountType.SAVING,LocalDate.now().minusYears(2));

        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2,savingAccountTest3));
        savingAccountTest1.setAccountNumber(savingAccountTest1.getEntityNumber() + savingAccountTest1.getBranchNumber() + savingAccountTest1.getId().toString());
        savingAccountTest2.setAccountNumber(savingAccountTest2.getEntityNumber() + savingAccountTest2.getBranchNumber() + savingAccountTest2.getId().toString());
        savingAccountTest3.setAccountNumber(savingAccountTest3.getEntityNumber() + savingAccountTest3.getBranchNumber() + savingAccountTest3.getId().toString());
        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2,savingAccountTest3));

        studentCheckingAccountTest1 = new StudentCheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.STUDENT_CHECKING);
        studentCheckingAccountTest2 = new StudentCheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.STUDENT_CHECKING);

        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));
        studentCheckingAccountTest1.setAccountNumber(studentCheckingAccountTest1.getEntityNumber() + studentCheckingAccountTest1.getBranchNumber() + studentCheckingAccountTest1.getId().toString());
        studentCheckingAccountTest2.setAccountNumber(studentCheckingAccountTest2.getEntityNumber() + studentCheckingAccountTest2.getBranchNumber() + studentCheckingAccountTest2.getId().toString());
        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        creditCardAccountRepository.deleteAll();
        savingAccountRepository.deleteAll();
        studentCheckingAccountRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();
        customUserDetails = null;
    }

    @Test
    void findAllAccounts_NotEmpty_ListOfAccounts() {
        customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(accountHolderTest1.getUsername());
        List<Account> accountList = accountService.findAllAccounts(customUserDetails);
        assertEquals(10, accountList.size());

    }

    @Test
    void getAccountBalance_validAccount_AccountBalance() {
        customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(accountHolderTest1.getUsername());
        String accountBalance = accountService.getAccountBalance(checkingAccountTest1.getAccountNumber(),customUserDetails);
        assertEquals("US$ 988.00",accountBalance);
    }

    @Test
    void createCheckingAccount() {
        Account checkingAccount = accountService.createCheckingAccount("0049","1234",new BigDecimal(1000),
                accountHolderTest1.getId(),accountHolderTest2.getId(),"test123");
        Optional<Account> optionalCheckingAccount = accountRepository.findById(checkingAccount.getId());
        assertTrue(optionalCheckingAccount.isPresent());
    }

    @Test
    void createSavingAccount() {
        Account savingAccount = accountService.createSavingAccount("0049","1234",new BigDecimal(1000),
                accountHolderTest1.getId(),accountHolderTest2.getId(),"test123",new BigDecimal(500),0.0025F);
        Optional<Account> optionalSavingAccount = accountRepository.findById(savingAccount.getId());
        assertTrue(optionalSavingAccount.isPresent());
    }

    @Test
    void createCreditCardAccount() {
        Account creditCardAccount = accountService.createCreditCardAccount("0049","1234",new BigDecimal(1000),
                accountHolderTest1.getId(),accountHolderTest2.getId(),new BigDecimal(5000),0.2F);
        Optional<Account> optionalCreditCardAccount = accountRepository.findById(creditCardAccount.getId());
        assertTrue(optionalCreditCardAccount.isPresent());
    }

    @Test
    void updateBalance() {
        accountService.updateBalance(checkingAccountTest1.getAccountNumber(),new BigDecimal(12500));
        Optional<Account> optionalCheckingAccount = accountRepository.findById(checkingAccountTest1.getId());
        assertEquals(optionalCheckingAccount.get().getBalance().getAmount(), new BigDecimal(12500).setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void applyInterestRate() {
        List<Account> accountList = accountRepository.findByPrimaryOwnerIdOrSecondaryOwnerId(accountHolderTest1.getId(),accountHolderTest1.getId());
        accountService.applyInterestRate(accountList);
        assertEquals(new BigDecimal("1002.50").setScale(2, RoundingMode.HALF_EVEN),accountList.get(6).getBalance().getAmount());
        assertEquals(new BigDecimal("2010.00").setScale(2, RoundingMode.HALF_EVEN),accountList.get(7).getBalance().getAmount());
        assertEquals(new BigDecimal("1050.01").setScale(2, RoundingMode.HALF_EVEN),accountList.get(3).getBalance().getAmount());
        assertEquals(new BigDecimal("1010.00").setScale(2, RoundingMode.HALF_EVEN),accountList.get(4).getBalance().getAmount());
    }

    @Test
    void applyMonthlyMaintenanceFee() {
        List<Account> accountList = accountRepository.findByPrimaryOwnerIdOrSecondaryOwnerId(accountHolderTest1.getId(),accountHolderTest1.getId());
        accountService.applyMonthlyMaintenanceFee(accountList);
        assertEquals(new BigDecimal("988.00").setScale(2, RoundingMode.HALF_EVEN),accountList.get(0).getBalance().getAmount());
        assertEquals(new BigDecimal("1000.00").setScale(2, RoundingMode.HALF_EVEN),accountList.get(1).getBalance().getAmount());
        assertEquals(new BigDecimal("176.00").setScale(2, RoundingMode.HALF_EVEN),accountList.get(2).getBalance().getAmount());

    }

    @Test
    void updateStatus_ValidStatus_StatusUpdate() {
        accountService.updateStatus(checkingAccountTest1.getAccountNumber());
        Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(checkingAccountTest1.getId());
        assertSame(optionalCheckingAccount.get().getStatus(), Status.FROZEN);
    }
}