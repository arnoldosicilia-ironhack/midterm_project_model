package com.deingun.bankingsystem.repository.account;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.StudentCheckingAccount;
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
class StudentCheckingAccountRepositoryTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    StudentCheckingAccount studentCheckingAccountTest1;
    StudentCheckingAccount studentCheckingAccountTest2;

    @BeforeEach
    void setUp() {

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));

        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));


        studentCheckingAccountTest1 = new StudentCheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.STUDENT_CHECKING);
        studentCheckingAccountTest2 = new StudentCheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.STUDENT_CHECKING);

        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));
        studentCheckingAccountTest1.setAccountNumber(studentCheckingAccountTest1.getEntityNumber() + studentCheckingAccountTest1.getBranchNumber() + studentCheckingAccountTest1.getId().toString());
        studentCheckingAccountTest2.setAccountNumber(studentCheckingAccountTest2.getEntityNumber() + studentCheckingAccountTest2.getBranchNumber() + studentCheckingAccountTest2.getId().toString());
        studentCheckingAccountRepository.saveAll(List.of(studentCheckingAccountTest1, studentCheckingAccountTest2));
    }

    @AfterEach
    void tearDown() {
        studentCheckingAccountRepository.deleteAll();
        userRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void findById_validId_isPresent() {
        Optional<StudentCheckingAccount> optionalStudentCheckingAccount1 = studentCheckingAccountRepository.findById(studentCheckingAccountTest1.getId());
        Optional<StudentCheckingAccount> optionalStudentCheckingAccount2 = studentCheckingAccountRepository.findById(studentCheckingAccountTest2.getId());
        assertTrue(optionalStudentCheckingAccount1.isPresent());
        assertTrue(optionalStudentCheckingAccount2.isPresent());
    }

    @Test
    void findById_invalidId_isEmpty() {
        Optional<StudentCheckingAccount> optionalStudentCheckingAccount1 = studentCheckingAccountRepository.findById(-1L);
        assertTrue(optionalStudentCheckingAccount1.isEmpty());
    }

    @Test
    void findByPrimaryOwnerId_validId_isPresent() {
        List<StudentCheckingAccount> studentCheckingAccountList = studentCheckingAccountRepository.findByPrimaryOwnerId(accountHolderTest1.getId());
        assertEquals(1, studentCheckingAccountList.size());
        Assertions.assertThat(studentCheckingAccountList.get(0).getBalance().getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(1000));
    }

    @Test
    void findAll_noParams_AccountList() {
        List<StudentCheckingAccount> studentCheckingAccountList = studentCheckingAccountRepository.findAll();
        assertEquals(2, studentCheckingAccountList.size());
    }
}