package com.example.document.repository;

import com.example.document.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query("DELETE FROM Otp o WHERE o.expiryTime > now()")
    void cleanExpiredOtp();
}
