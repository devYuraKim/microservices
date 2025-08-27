package com.example.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//TODO[API]: Consider removing this class and merging into ApiResponse<T> for unified structure
@Data
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String message;
}

