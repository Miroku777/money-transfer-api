package com.example.moneytransferapi.controllers;

import com.example.moneytransferapi.dto.TransferRequest;
import com.example.moneytransferapi.services.AccountService;
import com.example.moneytransferapi.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
@Tag(name = "Transfer", description = "API для переводов")
public class TransferController {
    private final AccountService accountService;

    @PostMapping
    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    @Operation(summary = "Make transfer", description = "Transfers money from the current user to the specified user")
    public void makeTransfer(@Valid @RequestBody TransferRequest request) {
        Long fromUserId = SecurityUtils.getCurrentUserId();
        accountService.makeTransfer(fromUserId, request.getToUserId(), request.getAmount());
    }
}