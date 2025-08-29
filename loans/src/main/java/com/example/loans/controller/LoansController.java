package com.example.loans.controller;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.ApiResponseDto;
import com.example.loans.dto.LoansDto;
import com.example.loans.service.ILoansService;
import com.example.loans.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans",
        description = "CREATE, FETCH, UPDATE and DELETE loan details")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {

    private final ILoansService iLoansService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<Void>> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits") String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.CREATED, LoansConstants.MESSAGE_201);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDto<LoansDto>> fetchLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits") String mobileNumber) {
        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK,LoansConstants.MESSAGE_200,loansDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<Void>> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        iLoansService.updateLoan(loansDto);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK,LoansConstants.MESSAGE_200);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<Void>> deleteLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits") String mobileNumber) {
        iLoansService.deleteLoan(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK,LoansConstants.MESSAGE_200);
    }

}
