package com.example.document.service.impl;

import com.example.document.dto.CreateCustomerDto;
import com.example.document.dto.CustomerDto;
import com.example.document.dto.MessageDto;
import com.example.document.dto.OtpDto;
import com.example.document.entity.Customer;
import com.example.document.dto.report.CustomerField;
import com.example.document.entity.Otp;
import com.example.document.exception.ConflictException;
import com.example.document.exception.ExceptionMessages;
import com.example.document.repository.CustomerRepository;
import com.example.document.service.CustomerService;
import com.example.document.service.MessageService;
import com.example.document.service.report.CustomerReportService;
import com.example.document.service.report.ExcelReportService;
import com.example.document.util.NumericTokenGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final CustomerReportService customerReportService;
    private final ExcelReportService reportService;

    private static final String REGISTRATION_OTP_SUB = "One time password for registration";
    private static final long OTP_EXPIRY_HOUR = 3;
    private static final int OTP_LENGTH = 4;

    @Override
    @Transactional
    public void createCustomer(CreateCustomerDto createCustomerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(createCustomerDto.getEmail());

        if(optionalCustomer.isPresent() && optionalCustomer.get().isEnabled()) {
            throw new ConflictException(ExceptionMessages.USER_ALREADY_EXISTS + ":" + createCustomerDto.getEmail());
        } else {
            String otpValue = NumericTokenGenerator.generateToken(OTP_LENGTH);
            CompletableFuture.runAsync(() ->
                    messageService.sendMessage(new MessageDto(createCustomerDto.getEmail(), REGISTRATION_OTP_SUB, otpValue))
            );
            if(optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                customer.setUsername(createCustomerDto.getEmail());
                customer.setPassword(passwordEncoder.encode(createCustomerDto.getPassword()));
                customer.setFirstName(createCustomerDto.getFirstName());
                customer.setLastName(createCustomerDto.getLastName());

                Otp otp = optionalCustomer.get().getOtp();
                otp.setValue(otpValue);
                otp.setExpiryTime(LocalDateTime.now().plusHours(OTP_EXPIRY_HOUR));
            } else {
                Customer customer = createCustomerDto.toCustomer(passwordEncoder);
                Otp otp = Otp.builder()
                        .value(otpValue)
                        .expiryTime(LocalDateTime.now().plusHours(OTP_EXPIRY_HOUR)).build();
                customer.setOtp(otp);
                otp.setCustomer(customer);
                customerRepository.save(customer);
            }
        }
    }

    @Override
    @Transactional
    public void confirmEmail(OtpDto otpDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(otpDto.getEmail());
        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if(customer.getOtp().isExpired()) {
                throw new CredentialsExpiredException(ExceptionMessages.OTP_EXPIRED);
            }
            else if(/*customer.getOtp().getValue().*/"0000".equals(otpDto.getOtp())) {
                customer.setOtp(null);
                customer.setEnabled(true);
            } else {
                throw new BadCredentialsException(ExceptionMessages.INVALID_OTP);
            }
        } else {
            throw new EntityNotFoundException(ExceptionMessages.USER_NOT_FOUND_BY_EMAIL + ":" + otpDto.getEmail());
        }
    }

    @Override
    public CustomerDto getCustomer() {
        return getLoggedInUser().toCustomerDto();
    }

    @Override
    public Customer getLoggedInUser() {
        String username = getPrincipal().getUsername();
        return customerRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.USER_NOT_FOUND_BY_EMAIL)
        );
    }

    @Override
    public UserDetails getPrincipal() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return (UserDetails) principal;
        } else {
            throw new AuthenticationException("Unauthorized") {
            };
        }
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public byte[] export() {
        return reportService.export(
                customerRepository.findAll(),
                CustomerField.values()
        );
    }
}
