package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.SavingAccount;
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

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SavingAccountRepositoryTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    SavingAccount savingAccountTest1;
    SavingAccount savingAccountTest2;
    SavingAccount savingAccountTest3;

    @BeforeEach
    void setUp() {
        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));
        BigDecimal minimumBalance = new BigDecimal("1000").setScale(3, RoundingMode.HALF_EVEN);

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));


        savingAccountTest1 = new SavingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc", minimumBalance, LocalDate.now(), Status.ACTIVE, 0.0050F, AccountType.SAVING, LocalDate.now());
        savingAccountTest2 = new SavingAccount("0049", "1500", balance, accountHolderTest2, accountHolderTest1, "123abc", minimumBalance, LocalDate.now(), Status.ACTIVE,AccountType.SAVING, LocalDate.now());
        savingAccountTest3 = new SavingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc", LocalDate.now(), Status.ACTIVE,AccountType.SAVING,LocalDate.now());

        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2, savingAccountTest3));
        savingAccountTest1.setAccountNumber(savingAccountTest1.getEntityNumber() + savingAccountTest1.getBranchNumber() + savingAccountTest1.getId().toString());
        savingAccountTest2.setAccountNumber(savingAccountTest2.getEntityNumber() + savingAccountTest2.getBranchNumber() + savingAccountTest2.getId().toString());
        savingAccountTest3.setAccountNumber(savingAccountTest3.getEntityNumber() + savingAccountTest3.getBranchNumber() + savingAccountTest3.getId().toString());
        savingAccountRepository.saveAll(List.of(savingAccountTest1, savingAccountTest2, savingAccountTest3));
    }

    @AfterEach
    void tearDown() {
        savingAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<SavingAccount> optionalSavingAccount = savingAccountRepository.findById(-1L);
        assertTrue(optionalSavingAccount.isEmpty());
    }

    @Test
    void findByPrimaryOwnerId_validId_isPresent() {
        List<SavingAccount> savingAccountList = savingAccountRepository.findByPrimaryOwnerId(accountHolderTest1.getId());
        assertEquals(2, savingAccountList.size());
        Assertions.assertThat(savingAccountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
        Assertions.assertThat(savingAccountList.get(0).getInterestRate())
                .isEqualByComparingTo(0.0050F);
        Assertions.assertThat(savingAccountList.get(1).getInterestRate())
                .isEqualByComparingTo(0.0025F);

    }

    @Test
    void findAll_noParams_accountList() {
        List<SavingAccount> savingAccountList = savingAccountRepository.findAll();
        assertEquals(3, savingAccountList.size());
    }

}