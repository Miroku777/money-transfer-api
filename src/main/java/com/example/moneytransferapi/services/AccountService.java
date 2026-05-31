package com.example.moneytransferapi.services;

import com.example.moneytransferapi.dao.AccountRepository;
import com.example.moneytransferapi.entityes.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    private static final BigDecimal TEN_PERCENT = new BigDecimal("0.1");
    private static final BigDecimal MAX_LIMIT = new BigDecimal("2.07");

    @Transactional
    public void makeTransfer(Long fromUserId, Long toUserId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        if (fromUserId.equals(toUserId)) {
            throw new RuntimeException("Cannot transfer to yourself");
        }
        Account fromAccount = accountRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
        Account toAccount = accountRepository.findByUserId(toUserId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient account not found"));
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        log.info("Transfer {} from {} to {}", amount, fromUserId, toUserId);
    }

    @Transactional
    public void increaseBalances() {
        accountRepository.increaseBalances(TEN_PERCENT, MAX_LIMIT);
        log.info("Account balance increased by 10%");
    }
}





