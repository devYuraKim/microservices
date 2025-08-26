package com.example.accounts.exception;

import com.example.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //extract a private helper method for building error responses
    private ResponseEntity<ErrorResponseDto> buildErrorResponse(
            WebRequest webRequest,
            Exception exception,
            HttpStatus httpStatus
    ) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
            webRequest.getDescription(false),
            httpStatus,
            exception.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(
            WebRequest webRequest, IllegalArgumentException exception
    ) {
        return buildErrorResponse(webRequest, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(WebRequest webRequest, Exception exception) {
        return buildErrorResponse(webRequest, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(
            WebRequest webRequest, CustomerAlreadyExistsException exception
    ) {
        return buildErrorResponse(webRequest, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            WebRequest webRequest, ResourceNotFoundException exception
    ) {
        return buildErrorResponse(webRequest, exception, HttpStatus.NOT_FOUND);
    }

}
