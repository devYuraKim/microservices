package com.example.accounts.controller;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.service.ICustomerDetailsService;
import com.example.accounts.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Customers in microservices",
        description = "REST APIs in microservices to FETCH customer details")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerDetailsController {

    private final ICustomerDetailsService iCustomerDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsController.class);

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<ApiResponseDto<CustomerDetailsDto>> fetchCustomerDetails(
            @RequestHeader("microservices-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")
            String mobileNumber){
        logger.debug("microservices-correlation-id found:{}", correlationId);
        CustomerDetailsDto customerDetailsDto = iCustomerDetailsService.fetchCustomerDetails(mobileNumber, correlationId);
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, "Customer details fetched successfully", customerDetailsDto);
    }

}
