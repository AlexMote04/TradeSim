package com.github.alexmote04.tradesim.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // This catches ANY IllegalArgumentException thrown anywhere in your Controllers or Services
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // We return a 400 Bad Request along with the exact message we wrote in our Service
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}