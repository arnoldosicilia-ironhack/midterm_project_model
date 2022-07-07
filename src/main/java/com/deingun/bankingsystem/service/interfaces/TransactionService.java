package com.deingun.bankingsystem.service.interfaces;

import com.deingun.bankingsystem.enums.TransactionType;
import com.deingun.bankingsystem.model.Transaction;
import com.deingun.bankingsystem.security.CustomUserDetails;

import java.math.BigDecimal;

public interface TransactionService {

    Transaction newTransaction(String originAccountNumber, String destinationAccountNumber, BigDecimal amount, CustomUserDetails customUserDetails);

    String newThirdPartyTransaction(String hashedKey, String AccountNumber, BigDecimal amount, String secretKey, TransactionType transactionType, CustomUserDetails customUserDetails);
}
