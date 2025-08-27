package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
    name = "Error Response",
    description = "Schema to represent error response"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "API path that triggered the error", example = "/api/create")
    private String apiPath;

    @Schema(description = "Error code", example = "BAD_REQUEST")
    private HttpStatus errorCode;

    @Schema(description = "Error message", example = "Customer already registered with given mobile number 01012345678")
    private String errorMessage;

    @Schema(description = "Error time", example = "2100-01-01T00:00:00")
    private LocalDateTime errorTime;
}