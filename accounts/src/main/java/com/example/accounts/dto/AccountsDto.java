package com.example.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message="Account number is required")
    @Pattern(regexp = "^\\d{10}$", message="Account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message="Account type is required")
    private String accountType;

    @NotEmpty(message="Branch address is required")
    private String branchAddress;
}