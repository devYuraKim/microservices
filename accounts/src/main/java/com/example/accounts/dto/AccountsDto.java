package com.example.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountsDto {

    //@NotEmpty(message="Account number is required")
    //@Pattern(regexp = "^\\d{10}$", message="Account number must be 10 digits")
    @NotNull(message="Account number is required")
    private Long accountNumber;

    @NotEmpty(message="Account type is required")
    private String accountType;

    @NotEmpty(message="Branch address is required")
    private String branchAddress;
}