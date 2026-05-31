package com.example.moneytransferapi.utils;

import com.example.moneytransferapi.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class BalanceScheduler {
    private final AccountService accountService;

    @Scheduled(fixedDelay = 30000)
    public void increaseBalances() {
        log.info("Starting scheduled balance increase");
        accountService.increaseBalances();
    }
}