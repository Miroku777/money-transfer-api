package com.example.moneytransferapi.dao;

import com.example.moneytransferapi.entityes.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
    @Modifying
    @Query("UPDATE Account a SET a.balance = LEAST(a.balance + a.initialBalance * :multiplier, " +
            "a.initialBalance * :maxPercent) " +
            "WHERE a.balance < a.initialBalance * :maxPercent")
    void increaseBalances(@Param("multiplier") BigDecimal multiplier,
                         @Param("maxPercent") BigDecimal maxPercent);
}