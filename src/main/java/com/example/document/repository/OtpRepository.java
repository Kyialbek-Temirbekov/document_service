package com.example.document.repository;

import com.example.document.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.expiryTime < CURRENT_TIMESTAMP ")
    void cleanExpiredOtp();
}
