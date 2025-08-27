package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
    name = "Success Response",
    description = "Schema to represent successful response")
@Data
@AllArgsConstructor
public class ResponseDto {
    @Schema(description = "Status code in the response", example = "200")
    private String status;
    @Schema(description = "Status message in the response", example = "Request processed successfully")
    private String message;
}

