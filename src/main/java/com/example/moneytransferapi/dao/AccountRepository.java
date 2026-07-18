package com.example.moneytransferapi.dao;

import com.example.moneytransferapi.entityes.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByUserId(Long userId);
    @Modifying
    @Query("UPDATE Account a SET a.balance = LEAST(a.balance + a.initialBalance * :multiplier, " +
            "a.initialBalance * :maxPercent) " +
            "WHERE a.balance < a.initialBalance * :maxPercent")
    void increaseBalances(@Param("multiplier") BigDecimal multiplier,
                         @Param("maxPercent") BigDecimal maxPercent);
}