package com.example.document.service;

import com.example.document.dto.CreateCustomerDto;
import com.example.document.dto.OtpDto;

public interface CustomerService {
    void createCustomer(CreateCustomerDto createCustomerDto);
    void confirmEmail(OtpDto otpDto);
}
