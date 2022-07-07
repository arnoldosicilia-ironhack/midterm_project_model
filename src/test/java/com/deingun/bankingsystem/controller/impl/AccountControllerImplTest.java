package com.deingun.bankingsystem.controller.impl;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.CheckingAccount;
import com.deingun.bankingsystem.model.account.SavingAccount;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.model.user.Admin;
import com.deingun.bankingsystem.repository.account.AccountRepository;
import com.deingun.bankingsystem.repository.account.CheckingAccountRepository;
import com.deingun.bankingsystem.repository.account.CreditCardAccountRepository;
import com.deingun.bankingsystem.repository.account.SavingAccountRepository;
import com.deingun.bankingsystem.repository.user.AccountHolderRepository;
import com.deingun.bankingsystem.repository.user.AdminRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import com.deingun.bankingsystem.utils.Address;
import com.deingun.bankingsystem.utils.Money;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder accountHolderTest1;
    AccountHolder accountHolderTest2;
    Admin adminTest1;
    CheckingAccount checkingAccountTest1;
    CheckingAccount checkingAccountTest2;
    CheckingAccount checkingAccountTest3;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        Money balance = new Money(new BigDecimal("1000"));
        accountHolderTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        accountHolderTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        accountHolderRepository.saveAll(List.of(accountHolderTest1, accountHolderTest2));
        adminTest1 = new Admin("adminTest1", passwordEncoder.encode("123456"), LocalDate.now(), "admin");
        adminRepository.save(adminTest1);

        checkingAccountTest1 = new CheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());
        checkingAccountTest2 = new CheckingAccount("0049", "2020", balance, accountHolderTest2, accountHolderTest1, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.CHECKING,LocalDate.now());
        checkingAccountTest3 = new CheckingAccount("0049", "3030", balance, accountHolderTest2, null, "123abc",
                LocalDate.now(), Status.ACTIVE,AccountType.CHECKING,LocalDate.now());

        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2,checkingAccountTest3));
        checkingAccountTest1.setAccountNumber(checkingAccountTest1.getEntityNumber() + checkingAccountTest1.getBranchNumber() + checkingAccountTest1.getId().toString());
        checkingAccountTest2.setAccountNumber(checkingAccountTest2.getEntityNumber() + checkingAccountTest2.getBranchNumber() + checkingAccountTest2.getId().toString());
        checkingAccountTest3.setAccountNumber(checkingAccountTest3.getEntityNumber() + checkingAccountTest3.getBranchNumber() + checkingAccountTest3.getId().toString());
        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2, checkingAccountTest3));
    }

    @AfterEach
    void tearDown() {
        creditCardAccountRepository.deleteAll();
        savingAccountRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllAccounts_NoParams_AllCheckingAccountsOfTheActiveUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accounts").with(httpBasic("accountHolderTest1", "123456")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("0049"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1500"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("123abc"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2020"));
        assertFalse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("3030"));

    }

    @Test
    void getAccountBalance_ValidParams_AccountBalance() throws Exception {

        String accountNumber = checkingAccountTest1.getAccountNumber();
        MvcResult mvcResult = mockMvc.perform(get("/accounts/" + accountNumber).with(httpBasic("accountHolderTest1", "123456")))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1000"));

    }

    @Test
    void getAccountBalance_InvalidParams_AccountBalance() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/accounts/00491500123").with(httpBasic("accountHolderTest1", "123456")))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }

    @Test
    void updateBalance_ValidParams_UpdateBalance() throws Exception {
        String accountNumber = checkingAccountTest1.getAccountNumber();
        String body = "{ \"amount\":\"2000\"}";

        MvcResult mvcResult = mockMvc.perform(patch("/accounts/" + accountNumber)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )
                .andExpect(status().isNoContent())
                .andReturn();

    }

    @Test
    void createCheckingAccount_Valid_NewCheckingAccount() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"secretKey\":\"1234abc\" }";
        MvcResult mvcResult = mockMvc.perform(post("/checkingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("0049"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2000"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1234abc"));

    }


    @Test
    void createCheckingAccount_UserWithoutPermisions_ForbiddenStatus() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"secretKey\":\"1234abc\" }";
        MvcResult mvcResult = mockMvc.perform(post("/checkingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("accountHolderTest1","123456"))
        )

                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void createCheckingAccount_InvalidData_UnprocessableEntityStatus() throws Exception {

        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":\"-1\", \"secretKey\":\"1234abc\" }";
        MvcResult mvcResult = mockMvc.perform(post("/checkingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    void createSavingAccount_Valid_NewCheckingAccount() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"secretKey\":\"1234abc\" , \"minimumBalance\":\"500\"}";
        MvcResult mvcResult = mockMvc.perform(post("/savingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("0049"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2000"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("1234abc"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("500"));
    }


    @Test
    void createSavingAccount_UserWithoutPermisions_ForbiddenStatus() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"secretKey\":\"1234abc\" , \"minimumBalance\":\"500\"}";
        MvcResult mvcResult = mockMvc.perform(post("/savingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("accountHolderTest1","123456"))
        )

                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void createSavingAccount_InvalidData_UnprocessableEntityStatus() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":\"-1\", \"secretKey\":\"1234abc\" , \"minimumBalance\":\"500\"}";
        MvcResult mvcResult = mockMvc.perform(post("/checkingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    void constraintViolationException_invalidInterestRate_constraintViolation() throws Exception {

        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":\"-1\", \"secretKey\":\"1234abc\" , \"minimumBalance\":\"50\"}";
        MvcResult mvcResult = mockMvc.perform(post("/checkingaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }


    @Test
    void createCreditCardAccount_Valid_NewCheckingAccount() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"creditLimit\":\"3000\"}";
        MvcResult mvcResult = mockMvc.perform(post("/creditcardaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("0049"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("2000"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("3000"));
    }


    @Test
    void createCreditCardAccount_UserWithoutPermisions_ForbiddenStatus() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":" + primaryOwnerId + ", \"creditLimit\":\"2000\"}";
        MvcResult mvcResult = mockMvc.perform(post("/creditcardaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("accountHolderTest1","123456"))
        )

                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void createCreditCardAccount_InvalidData_UnprocessableEntityStatus() throws Exception {
        Long primaryOwnerId = accountHolderTest1.getId();
        String body = "{ \"entityNumber\":\"0049\" , \"branchNumber\":\"1234\", \"amount\":\"2000\", \"primaryOwnerId\":\"-1\", \"creditLimit\":\"500\"}";
        MvcResult mvcResult = mockMvc.perform(post("/creditcardaccounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("adminTest1","123456"))
        )
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }
}