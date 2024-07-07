package com.example.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_id_gen")
    @SequenceGenerator(name = "otp_id_gen", sequenceName = "otp_seq", allocationSize = 1)
    private Long id;
    private String value;
    private LocalDateTime expiryTime;
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}
