package com.example.document.dto;

import com.example.document.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDto {
    private String email;
    private String password;
    private String name;

    public Customer toCustomer(PasswordEncoder passwordEncoder) {
        return Customer.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .name(name).build();
    }
}
