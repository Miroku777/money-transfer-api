package com.example.moneytransferapi.dao;

import com.example.moneytransferapi.entityes.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    Optional<PhoneData> findByPhone(String phone);
    boolean existsByPhone(String phone);
    @Modifying
    @Query("DELETE FROM PhoneData p WHERE p.id = :phoneId AND p.user.id = :userId")
    void deleteByUserIdAndPhoneId(@Param("userId") Long userId, @Param("phoneId") Long phoneId);
}