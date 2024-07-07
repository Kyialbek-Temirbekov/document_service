package com.example.document.service;

import com.example.document.dto.CreateCustomerDto;
import com.example.document.dto.CustomerDto;
import com.example.document.dto.OtpDto;
import com.example.document.entity.Customer;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerService {
    void createCustomer(CreateCustomerDto createCustomerDto);
    void confirmEmail(OtpDto otpDto);
    Customer getLoggedInUser();
    UserDetails getPrincipal();
    CustomerDto getCustomer();
}
