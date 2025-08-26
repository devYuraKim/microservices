package com.example.accounts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message="Name is required")
    @Size(min = 2, max = 50, message="Name should be between 2 and 50 characters")
    private String name;

    @NotEmpty(message="Email is required")
    @Email(message="Email should be valid")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")
    private String mobileNumber;

    @Valid
    private AccountsDto accountsDto;
}
