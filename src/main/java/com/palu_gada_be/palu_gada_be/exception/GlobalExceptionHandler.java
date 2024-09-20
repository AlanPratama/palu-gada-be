package com.palu_gada_be.palu_gada_be.exception;

import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.util.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .statusText(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("You do not have permission to access this resource")
                .errors(Collections.singletonList("Access Denied"))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse<String>> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .statusText(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message("Your session has expired. Please login again.")
                .errors(Collections.singletonList("JWT Token Expired"))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType().isEnum()) {
            String allowedValues = Arrays.stream(ex.getRequiredType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            String errorMessage = String.format("Invalid value '%s' for enum type %s. Allowed values are: %s",
                    ex.getValue(),
                    ex.getRequiredType().getSimpleName(),
                    allowedValues);

            ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                    .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .statusText(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(errorMessage)
                    .errors(Collections.singletonList("Invalid Enum Value"))
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // If the required type is not an enum, use the default error message
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .statusText(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Invalid argument type")
                .errors(Collections.singletonList("Argument Type Mismatch"))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
