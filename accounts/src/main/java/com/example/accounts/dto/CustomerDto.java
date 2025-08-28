package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
    description = "Schema to represent Customer and Account details"
)
public class CustomerDto {

    @Schema(
        description = "Name of the customer",
        example = "John Doe"
    )
    @NotEmpty(message="Name is required")
    @Size(min = 2, max = 50, message="Name should be between 2 and 50 characters")
    private String name;

    @Schema(
        description = "Email of the customer",
        example = "john.doe@example.com")
    @NotEmpty(message="Email is required")
    @Email(message="Email should be valid")
    private String email;

    @Schema(
        description = "Mobile number of the customer",
        example = "01012345678"
    )
    @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")
    private String mobileNumber;

    @Schema(
        description = "Accounts details of the customer"
    )
    @Valid
    private AccountsDto accountsDto;
}
