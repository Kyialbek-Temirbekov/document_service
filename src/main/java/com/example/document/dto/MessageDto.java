package com.example.document.dto;

public record MessageDto(
        String recipient,
        String subject,
        String body
) {
}
