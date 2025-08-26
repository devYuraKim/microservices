package com.example.accounts.exception;

import com.example.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    //TODO: add @ExceptionHandler(ConstraintViolationException.class)
    //NOTE: this method handles exceptions only triggered by @Valid on @RequestBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrorsMap = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String validationMessage = error.getDefaultMessage();
            validationErrorsMap.put(fieldName, validationMessage);
        });
        return ResponseEntity.badRequest().body(validationErrorsMap);
    }
}
