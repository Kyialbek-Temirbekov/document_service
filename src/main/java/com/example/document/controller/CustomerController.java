package com.example.document.controller;

import com.example.document.dto.CreateCustomerDto;
import com.example.document.dto.OtpDto;
import com.example.document.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Service")
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "create customer")
    @PostMapping()
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        customerService.createCustomer(createCustomerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Operation(summary = "confirm email")
    @PatchMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestBody OtpDto otpDto) {
        customerService.confirmEmail(otpDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
