package com.palu_gada_be.palu_gada_be.exception;

import com.palu_gada_be.palu_gada_be.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse<String>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .statusText(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .errors(Collections.singletonList("Runtime Exception occurred"))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .statusText(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Invalid username or password")
                .errors(Collections.singletonList("Bad Credentials"))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .statusText(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
