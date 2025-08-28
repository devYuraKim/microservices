package com.example.loans.util;

import com.example.loans.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResponseBuilder {

    /**
     * Returns a ResponseEntity of a successful API response WITH payload.
     *
     * Wraps the provided data in a unified {@link ApiResponseDto}, setting the HTTP status code,
     * status name, and a human-readable message.
     *
     * @param <T>     the type of the data
     * @param status  the {@link HttpStatus} enum of the response - provides the numeric HTTP code and the name of the status
     * @param message a human-readable custom message describing the outcome
     * @param payload    the data to be returned in the response
     * @return a {@link ResponseEntity} containing the API response payload with HTTP status set
     */
    public static <T> ResponseEntity<ApiResponseDto<T>> buildSuccessResponse(HttpStatus status, String message, T payload) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponseDto<>(
                        status.value(),
                        status.name(),
                        message,
                        payload,
                        null,
                        null
                ));
    }

    /**
     * Returns a ResponseEntity of a successful API response WITHOUT payload.
     *
     * The `data` field in {@link ApiResponseDto} will be null.
     *
     * @param status  the HttpStatus enum of the response - contains the numeric HTTP code and the name of the status
     * @param message a human-readable custom message describing the outcome
     * @return a {@link ResponseEntity} of the successful API response with HTTP status set
     */
    public static ResponseEntity<ApiResponseDto<Void>> buildSuccessResponseWithoutPayload(HttpStatus status, String message) {
        return buildSuccessResponse(status, message, null);
    }

    /**
     * Returns a ResponseEntity of a failed API response.
     *
     * The response body will have `data` set to null, `path` set to the endpoint that caused the error,
     * and `timestamp` automatically set to the current time.
     *
     * @param status  the {@link HttpStatus} enum - provides the numeric HTTP code and the name of the status
     * @param webRequest the {@link WebRequest} from which the API path is extracted
     * @param exception  the {@link Exception} whose message will be included in the response
     * @return a {@link ResponseEntity} containing the API response body and HTTP status
     */
    public static ResponseEntity<ApiResponseDto<Void>> buildErrorResponse(HttpStatus status, WebRequest webRequest, Exception exception) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponseDto<>(
                        status.value(),
                        status.name(),
                        exception.getMessage(),
                        null,
                        webRequest.getDescription(false).replaceFirst("uri=", ""),
                        LocalDateTime.now()
                ));
    }

    public static ResponseEntity<ApiResponseDto<Map<String, String>>> buildValidationErrorResponse(
            Map<String, String> errors, WebRequest webRequest
    ) {
        ApiResponseDto<Map<String, String>> response = new ApiResponseDto<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                "Validation failed",
                errors,
                webRequest.getDescription(false).replaceFirst("uri=", ""),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
