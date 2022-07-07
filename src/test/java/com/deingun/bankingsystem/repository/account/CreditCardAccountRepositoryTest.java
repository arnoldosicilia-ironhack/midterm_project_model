package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.model.account.CreditCardAccount;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCardAccountRepositoryTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    CreditCardAccount creditCardAccountTest1;
    CreditCardAccount creditCardAccountTest2;
    CreditCardAccount creditCardAccountTest3;

    @BeforeEach
    void setUp() {

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));


        creditCardAccountTest1 = new CreditCardAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, AccountType.CREDIT_CARD,LocalDate.now());
        creditCardAccountTest2 = new CreditCardAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, new BigDecimal("500"), 0.15F, AccountType.CREDIT_CARD,LocalDate.now());
        creditCardAccountTest3 = new CreditCardAccount("0049", "2020", balance, accountHolderTest1, null, new BigDecimal("600"),AccountType.CREDIT_CARD,LocalDate.now());

        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2, creditCardAccountTest3));
        creditCardAccountTest1.setAccountNumber(creditCardAccountTest1.getEntityNumber() + creditCardAccountTest1.getBranchNumber() + creditCardAccountTest1.getId().toString());
        creditCardAccountTest2.setAccountNumber(creditCardAccountTest2.getEntityNumber() + creditCardAccountTest2.getBranchNumber() + creditCardAccountTest2.getId().toString());
        creditCardAccountTest3.setAccountNumber(creditCardAccountTest3.getEntityNumber() + creditCardAccountTest3.getBranchNumber() + creditCardAccountTest3.getId().toString());
        creditCardAccountRepository.saveAll(List.of(creditCardAccountTest1, creditCardAccountTest2, creditCardAccountTest3));
    }

    @AfterEach
    void tearDown() {
        creditCardAccountRepository.deleteAll();
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void findById_validId_isPresent() {
        Optional<CreditCardAccount> optionalCreditCardAccount1 = creditCardAccountRepository.findById(creditCardAccountTest1.getId());
        Optional<CreditCardAccount> optionalCreditCardAccount2 = creditCardAccountRepository.findById(creditCardAccountTest2.getId());
        Optional<CreditCardAccount> optionalCreditCardAccount3 = creditCardAccountRepository.findById(creditCardAccountTest3.getId());
        assertTrue(optionalCreditCardAccount1.isPresent());
        assertTrue(optionalCreditCardAccount2.isPresent());
        assertTrue(optionalCreditCardAccount3.isPresent());

        assertEquals("accountHolderTest1", optionalCreditCardAccount1.get().getPrimaryOwner().getUsername());
        assertEquals(new BigDecimal("100.00"), optionalCreditCardAccount1.get().getCreditLimit());
        assertEquals(new BigDecimal("500.00"), optionalCreditCardAccount2.get().getCreditLimit());
        assertEquals(new BigDecimal("600.00"), optionalCreditCardAccount3.get().getCreditLimit());
        assertEquals(0.15F, optionalCreditCardAccount2.get().getInterestRate());
        assertEquals(0.2F, optionalCreditCardAccount3.get().getInterestRate());
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<CreditCardAccount> optionalCreditCardAccount1 = creditCardAccountRepository.findById(-1L);
        assertTrue(optionalCreditCardAccount1.isEmpty());
    }

    @Test
    void findByPrimaryOwnerId_validId_isPresent() {
        List<CreditCardAccount> creditCardAccountList = creditCardAccountRepository.findByPrimaryOwnerId(accountHolderTest1.getId());
        assertEquals(2, creditCardAccountList.size());
        Assertions.assertThat(creditCardAccountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
        Assertions.assertThat(creditCardAccountList.get(0).getCreditLimit())
                .isEqualByComparingTo(BigDecimal.valueOf(100));
        Assertions.assertThat(creditCardAccountList.get(0).getInterestRate())
                .isEqualByComparingTo(0.2F);
        Assertions.assertThat(creditCardAccountList.get(1).getCreditLimit())
                .isEqualByComparingTo(BigDecimal.valueOf(600));
    }

    @Test
    void findAll_noParams_accountList() {
        List<CreditCardAccount> creditCardAccountList = creditCardAccountRepository.findAll();
        assertEquals(3, creditCardAccountList.size());
    }


}