package com.example.document.exception;

import com.example.document.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerRestAdvice {
    private final ExceptionProperties properties;

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleException(AuthenticationException e) {
        return new ErrorResponseDto(HttpStatus.UNAUTHORIZED, properties.getMessage(e));
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleException(AccessDeniedException e) {
        return new ErrorResponseDto(HttpStatus.FORBIDDEN, properties.getMessage(e));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleException(EntityNotFoundException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND, properties.getMessage(e));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleException(IllegalArgumentException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST, properties.getMessage(e));
    }
    @ExceptionHandler(MinioException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(MinioException e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, properties.getMessageAndLog(e));
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(RuntimeException e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, properties.getMessageAndLog(e));
    }
}
