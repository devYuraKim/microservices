package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(
    name ="Accounts",
    description = "Schema to represent Accounts details")
@Data
public class AccountsDto {

    // NOTE: Validation constraints must be applied to compatible data types
    //@NotEmpty(message="Account number is required")
    //@Pattern(regexp = "^\\d{10}$", message="Account number must be 10 digits")
    @Schema(description = "Account number", example = "1234567890")
    @NotNull(message="Account number is required")
    private Long accountNumber;

    @Schema(description = "Account type", example = "Savings")
    @NotEmpty(message="Account type is required")
    private String accountType;

    @Schema(description = "Branch address", example = "123 Main St, Anytown, USA")
    @NotEmpty(message="Branch address is required")
    private String branchAddress;
}