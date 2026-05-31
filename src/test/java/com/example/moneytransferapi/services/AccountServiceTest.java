package com.example.moneytransferapi.services;

import com.example.moneytransferapi.dao.AccountRepository;
import com.example.moneytransferapi.entityes.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("1000.00"));

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("500.00"));
    }

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(toAccount));

        accountService.makeTransfer(1L, 2L, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("800.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("700.00"), toAccount.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientBalance() {
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(toAccount));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.makeTransfer(1L, 2L, new BigDecimal("2000.00"));
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenNegativeAmount() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.makeTransfer(1L, 2L, new BigDecimal("-100.00"));
        });

        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSenderNotFound() {
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.makeTransfer(1L, 2L, new BigDecimal("100.00"));
        });

        assertEquals("Sender account not found", exception.getMessage());
    }
}