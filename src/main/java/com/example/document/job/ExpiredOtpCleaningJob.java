package com.example.document.job;

import com.example.document.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredOtpCleaningJob {
    private final OtpRepository otpRepository;

    @Scheduled(cron = "*/10 * * * * *")
    public void cleanExpiredOtp() {
        log.info("Started cleaning expired otp");
        otpRepository.cleanExpiredOtp();
        log.info("Ended cleaning expired otp");
    }
}
