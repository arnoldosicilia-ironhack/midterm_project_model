package com.deingun.bankingsystem.validations;

import com.deingun.bankingsystem.model.user.AccountHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataValidationTest {

    DataValidation dataValidation;

    @Test
    void validateName_ValidName_False() {
        String name = "Pepito López";
        Boolean invalidName = DataValidation.validateName(name);
        assertFalse(invalidName);
    }

    @Test
    void validateName_InvalidName_True() {
        String name = "Pepito";
        Boolean invalidName = DataValidation.validateName(name);
        assertTrue(invalidName);
    }

    @Test
    void validateAmount_ValidAmount_False() {
        BigDecimal amount = new BigDecimal(100);
        Boolean invalidAmount = DataValidation.validateAmount(amount);
        assertFalse(invalidAmount);
    }

    @Test
    void validateAmount_InvalidAmount_True() {
        BigDecimal amount = new BigDecimal(-100);
        Boolean invalidAmount = DataValidation.validateAmount(amount);
        assertTrue(invalidAmount);
    }


    @Test
    void validateAgeOfPrimaryOwner_LessThan24_True() {
        AccountHolder accountHolder = new AccountHolder("accountHolderTest1", "123456", LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(2005, 10, 5), null, "test@gmail.com");
        Boolean lessThan24 = DataValidation.validateAgeOfPrimaryOwner(accountHolder);
        assertTrue(lessThan24);
    }

    @Test
    void validateAgeOfPrimaryOwner_LessThan24_False() {
        AccountHolder accountHolder = new AccountHolder("accountHolderTest1", "123456", LocalDate.now(), "NameTest1", "11111111A", LocalDate.of(1980, 10, 5), null, "test@gmail.com");
        Boolean lessThan24 = DataValidation.validateAgeOfPrimaryOwner(accountHolder);
        assertFalse(lessThan24);
    }

}