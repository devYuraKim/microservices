package com.example.accounts.controller;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.service.ICustomerDetailsService;
import com.example.accounts.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs for Customers in microservices",
        description = "REST APIs in microservices to FETCH customer details")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerDetailsController {

    private final ICustomerDetailsService iCustomerDetailsService;

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<ApiResponseDto<CustomerDetailsDto>> fetchCustomerDetails(
            @RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")
            String mobileNumber){

        CustomerDetailsDto customerDetailsDto = iCustomerDetailsService.fetchCustomerDetails(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, "Customer details fetched successfully", customerDetailsDto);
    }

}
