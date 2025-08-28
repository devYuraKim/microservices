package com.example.loans.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(
        name = "API Response",
        description = "Unified schema for API responses"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {

    @Schema(description = "Status code", example = "200")
    private int code;

    @Schema(description = "Status message", example = "OK")
    private String status;

    @Schema(description = "Human-readable message", example = "Request processed successfully")
    private String message;

    @Schema(description = "Data returned in success case, null for errors")
    private T data;

    @Schema(description = "Error path (only for errors)", example = "/api/create")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    @Schema(description = "Error timestamp (only for errors)")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timestamp;

}

