package com.deingun.bankingsystem.controller.impl;

import com.deingun.bankingsystem.enums.AccountType;
import com.deingun.bankingsystem.enums.Status;
import com.deingun.bankingsystem.model.account.CheckingAccount;
import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.model.user.Admin;
import com.deingun.bankingsystem.model.user.ThirdParty;
import com.deingun.bankingsystem.repository.TransactionRepository;
import com.deingun.bankingsystem.repository.account.AccountRepository;
import com.deingun.bankingsystem.repository.account.CheckingAccountRepository;
import com.deingun.bankingsystem.repository.account.CreditCardAccountRepository;
import com.deingun.bankingsystem.repository.account.SavingAccountRepository;
import com.deingun.bankingsystem.repository.user.AccountHolderRepository;
import com.deingun.bankingsystem.repository.user.AdminRepository;
import com.deingun.bankingsystem.repository.user.ThirdPartyRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import com.deingun.bankingsystem.utils.Address;
import com.deingun.bankingsystem.utils.Money;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.bridge.IMessage;
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

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TransactionControllerImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

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
    ThirdParty thirdPartyTest1;

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
        thirdPartyTest1 = new ThirdParty("thirdPartyTest1", passwordEncoder.encode("123456"), LocalDate.now(), "nameTest1", "123abc");
        thirdPartyRepository.save(thirdPartyTest1);

        checkingAccountTest1 = new CheckingAccount("0049", "1500", balance, accountHolderTest1, accountHolderTest2, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());
        checkingAccountTest2 = new CheckingAccount("0049", "2020", balance, accountHolderTest2, null, "123abc",
                LocalDate.now(), Status.ACTIVE, AccountType.CHECKING,LocalDate.now());

        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));
        checkingAccountTest1.setAccountNumber(checkingAccountTest1.getEntityNumber() + checkingAccountTest1.getBranchNumber() + checkingAccountTest1.getId().toString());
        checkingAccountTest2.setAccountNumber(checkingAccountTest2.getEntityNumber() + checkingAccountTest2.getBranchNumber() + checkingAccountTest2.getId().toString());
        checkingAccountRepository.saveAll(List.of(checkingAccountTest1, checkingAccountTest2));
    }

    @AfterEach
    void tearDown() {

        transactionRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void newTransaction_validParams_Transaction() throws Exception {

        String originAccountNumber = checkingAccountTest1.getAccountNumber();
        String destinationAccountNumber = checkingAccountTest2.getAccountNumber();

        String body = "{ \"originAccount\": \"" + originAccountNumber +  "\", \"destinationAccount\": \""+ destinationAccountNumber + "\", \"amount\":\"100\" }";

        MvcResult mvcResult = mockMvc.perform(post("/transactions")
                .with(httpBasic("accountHolderTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")

        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("100"));

    }

    @Test
    void newTransaction_InvalidAccount_IsUnprocessable() throws Exception {

        String body = "{ \"originAccount\":\"00491500123\" , \"destinationAccount\":\"004920202\", \"amount\":\"100\" }";
        MvcResult mvcResult = mockMvc.perform(post("/transactions")
                .with(httpBasic("accountHolderTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("accountHolderTest1", "123456"))
        )

                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }

    @Test
    void newTransaction_InvalidAmount_IsUnprocessable() throws Exception {

        String body = "{ \"originAccount\":\"004915001\" , \"destinationAccount\":\"004920202\", \"amount\":\"10000\" }";
        MvcResult mvcResult = mockMvc.perform(post("/transactions")
                .with(httpBasic("accountHolderTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("accountHolderTest1", "123456"))
        )

                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }

    @Test
    void newThirdPartyTransaction_ValidParams_Transaction() throws Exception {

        String accountNumber = checkingAccountTest1.getAccountNumber();

        String body = "{ \"accountNumber\":\""+ accountNumber + "\" , \"amount\":\"100\", \"secretKey\":\"123abc\", \"transactionType\":\"DEBIT\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdpartytransactions")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("thirdPartyTest1", "123456"))
                .header("Hashed-Key", "123abc")
        )

                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("100"));
    }

    @Test
    void newThirdPartyTransaction_InvalidAccount_IsUnprocessable() throws Exception {

        String body = "{ \"accountNumber\":\"00491500123\" , \"amount\":\"100\", \"secretKey\":\"123abc\", \"transactionType\":\"DEBIT\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdpartytransactions")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("thirdPartyTest1", "123456"))
                .header("Hashed-Key", "123abc")
        )

                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }

    @Test
    void newThirdPartyTransaction_InvalidHasheKey_IsUnprocessable() throws Exception {

        String body = "{ \"accountNumber\":\"004915001\" , \"amount\":\"100\", \"secretKey\":\"123abc\", \"transactionType\":\"DEBIT\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdpartytransactions")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("thirdPartyTest1", "123456"))
                .header("Hashed-Key", "xxxxx")
        )

                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }

    @Test
    void newThirdPartyTransaction_InvalidAmount_IsUnprocessable() throws Exception {

        String body = "{ \"accountNumber\":\"004915001\" , \"amount\":\"10000\", \"secretKey\":\"123abc\", \"transactionType\":\"DEBIT\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdpartytransactions")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(httpBasic("thirdPartyTest1", "123456"))
                .header("Hashed-Key", "123abc")
        )

                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }
}