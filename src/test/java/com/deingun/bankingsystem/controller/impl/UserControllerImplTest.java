package com.deingun.bankingsystem.controller.impl;

import com.deingun.bankingsystem.model.user.AccountHolder;
import com.deingun.bankingsystem.model.user.Admin;
import com.deingun.bankingsystem.model.user.ThirdParty;
import com.deingun.bankingsystem.model.user.User;
import com.deingun.bankingsystem.repository.user.AccountHolderRepository;
import com.deingun.bankingsystem.repository.user.AdminRepository;
import com.deingun.bankingsystem.repository.user.ThirdPartyRepository;
import com.deingun.bankingsystem.repository.user.UserRepository;
import com.deingun.bankingsystem.utils.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerImplTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AdminRepository adminRepository;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    User userTest1;
    User userTest2;
    User userTest3;
    User userTest4;
    User userTest5;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        Address addressTest = new Address("streetTest", "cityTest", "countryTest", 22222);
        userTest1 = new AccountHolder("accountHolderTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), addressTest, "test@gmail.com");
        userTest2 = new AccountHolder("accountHolderTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest2", "22222222F", LocalDate.of(1990, 2, 15), addressTest, "test@gmail.com");
        userTest3 = new Admin("adminTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest3");
        userTest4 = new ThirdParty("thirdPartyTest1", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest4", "abc123");
        userTest5 = new ThirdParty("thirdPartyTest2", passwordEncoder.encode("123456"), LocalDate.now(), "NameTest5", "abc123");
        userRepository.saveAll(List.of(userTest1, userTest2, userTest3, userTest4, userTest5));

    }

    @AfterEach
    void tearDown() {

        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void findAll_NoParams_UsersList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/users").with(httpBasic("adminTest1", "123456")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest2"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest3"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest4"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest5"));

    }

    @Test
    void findAll_InvalidUser_UsersList() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/users").with(httpBasic("accountHolderTest1", "123456")))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void findById_Valid_AccountHolder() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/accountholders/" + userTest1.getId())
                .with(httpBasic("adminTest1", "123456")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("accountHolderTest1"));

    }

    @Test
    void findById_InValidUser_IsForbidden() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/accountholders/" + userTest1.getId())
                .with(httpBasic("accountHolderTest2", "123456")))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void findById_InValidId_IsNotFound() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/accountholders/99999")
                .with(httpBasic("adminTest1", "123456")))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void findAllAccountHolders_NoParams_AccountHoldersList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/accountholders").with(httpBasic("adminTest1", "123456")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest1"));
        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest2"));
        assertFalse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest3"));
        assertFalse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest4"));
        assertFalse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("NameTest5"));
    }

    @Test
    void createAccountHolder_Valid_NewAccountHolder() throws Exception {
        String body = "{\"username\": \"accountHolderTest3\",\"password\": \"123456\"," +
                "\"name\": \"Julian Muñoz\",\"nif\": \"111111111Z\",\"dateOfBirth\": \"2000-03-03\"," +
                "\"street\": \"streetTest\",\"city\": \"cityTest\",\"country\": \"countryTest\"," +
                "\"postalCode\": 66666,\"mailingAddress\": \"test@gmail.com\" }";

        MvcResult mvcResult = mockMvc.perform(post("/accountholders").with(httpBasic("adminTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("accountHolderTest3"));
    }

    @Test
    void createAccountHolder_InvalidUser_IsForbidden() throws Exception {
        String body = "{\"username\": \"accountHolderTest3\",\"password\": \"123456\"," +
                "\"name\": \"Julian Muñoz\",\"nif\": \"111111111Z\",\"dateOfBirth\": \"2000-03-03\"," +
                "\"street\": \"streetTest\",\"city\": \"cityTest\",\"country\": \"countryTest\"," +
                "\"postalCode\": 66666,\"mailingAddress\": \"test@gmail.com\" }";

        MvcResult mvcResult = mockMvc.perform(post("/accountholders").with(httpBasic("accountHolderTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void createAccountHolder_InvalidData_IsBadRequest() throws Exception {
        String body = "{\"username\": \"accountHolderTest3\",\"password\": \"123456\"," +
                "\"nif\": \"111111111Z\",\"dateOfBirth\": \"2000-03-03\"," +
                "\"street\": \"streetTest\",\"city\": \"cityTest\",\"country\": \"countryTest\"," +
                "\"postalCode\": 66666,\"mailingAddress\": \"test@gmail.com\" }";

        MvcResult mvcResult = mockMvc.perform(post("/accountholders").with(httpBasic("adminTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void createThirdParty_Valid_NewThirdParty() throws Exception {
        String body = "{\"username\": \"BBVA\",\"password\": \"123456\"," +
                "\"name\": \"Banco BBVA\",\"hashedKey\": \"123abc\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdparties").with(httpBasic("adminTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("BBVA"));
    }

    @Test
    void createThirdParty_InvalidUser_IsForbidden() throws Exception {
        String body = "{\"username\": \"BBVA\",\"password\": \"123456\"," +
                "\"name\": \"Banco BBVA\",\"hashedKey\": \"123abc\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdparties").with(httpBasic("accountHolderTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void createThirdParty_InvalidData_IsBadRequest() throws Exception {
        String body = "{\"username\": \"BBVA\",\"password\": \"123456\"," +
                "\"hashedKey\": \"123abc\" }";

        MvcResult mvcResult = mockMvc.perform(post("/thirdparties").with(httpBasic("adminTest1", "123456"))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateAccountHolder() {
    }
}