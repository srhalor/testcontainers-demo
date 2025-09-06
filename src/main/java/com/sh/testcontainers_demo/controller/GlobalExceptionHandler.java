package com.sh.testcontainers_demo.controller;

import com.sh.testcontainers_demo.exception.AddressNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions thrown by controllers and provides
 * appropriate HTTP responses.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles AddressNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the AddressNotFoundException
     * @return ResponseEntity with error message and 404 status
     */
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> handleAddressNotFound(AddressNotFoundException ex) {
        log.error("Address not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
