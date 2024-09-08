package com.example.document.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Time Service")
@RestController
@RequestMapping("/time")
public class TimeController {
    @Operation(summary = "Current date time")
    @GetMapping("/now")
    public ResponseEntity<?> getCustomer() {
        return new ResponseEntity<>(LocalDateTime.now(), HttpStatus.OK);
    }
}
