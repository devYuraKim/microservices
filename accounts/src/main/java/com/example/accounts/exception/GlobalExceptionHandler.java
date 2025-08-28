package com.example.accounts.exception;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.util.ApiResponseBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //400 BAD REQUEST - handles manually thrown errors (e.g., AccountServiceImpl updateAccount() customerDto == null)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgumentException(WebRequest webRequest, IllegalArgumentException exception) {
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, webRequest, exception);
    }

    //TODO: Look into the classification and handling of validation exceptions and how constraint violations are collected and wrapped in the unified API response
    //MethodArgumentNotValid: Has a default handler in ResponseEntityExceptionHandler called handleMethodArgumentNotValid
    //ConstraintViolationException: Has no default handler

    //400 BAD REQUEST
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleConstraintViolationException(WebRequest webRequest, ConstraintViolationException exception) {
        Map<String, String> errors = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));

        return ApiResponseBuilder.buildValidationErrorResponse(errors, webRequest);
    }

    //400 BAD REQUEST
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders _headers,
            HttpStatusCode _status,
            WebRequest request
    ) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        return (ResponseEntity<Object>) (ResponseEntity<?>) ApiResponseBuilder.buildValidationErrorResponse(errors, request);
    }

    //409 CONFLICT
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleCustomerAlreadyExistsException(WebRequest webRequest, CustomerAlreadyExistsException exception) {
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.CONFLICT, webRequest, exception);
    }

    //404 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleResourceNotFoundException(WebRequest webRequest, ResourceNotFoundException exception) {
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, webRequest, exception);
    }

    //500 INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGlobalException(WebRequest webRequest, Exception exception) {
        log.error("Unhandled exception at {}: {}", webRequest.getDescription(false), exception.getMessage(), exception);
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, webRequest, exception);
    }

}
