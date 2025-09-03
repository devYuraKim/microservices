package com.example.loans.controller;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.ApiResponseDto;
import com.example.loans.dto.LoansContactInfoDto;
import com.example.loans.dto.LoansDto;
import com.example.loans.service.ILoansService;
import com.example.loans.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans",
        description = "CREATE, FETCH, UPDATE and DELETE loan details")
//@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {

    private final ILoansService iLoansService;
    private final LoansContactInfoDto loansContactInfoDto;
    private final static Logger logger = LoggerFactory.getLogger(LoansController.class);

    public LoansController(ILoansService iLoansService, LoansContactInfoDto loansContactInfoDto) {
        this.iLoansService = iLoansService;
        this.loansContactInfoDto = loansContactInfoDto;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<Void>> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits") String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.CREATED, LoansConstants.MESSAGE_201);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponseDto<LoansDto>> fetchLoanDetails(
            @RequestHeader("microservices-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{11})", message = "Mobile number must be 11 digits") String mobileNumber) {
        logger.debug("microservices-correlation-id found{}", correlationId);
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

    @GetMapping("/build-info")
    public ResponseEntity<ApiResponseDto<Void>> getBuildInfo(){
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, "Loans build info");
    }

    @GetMapping("/java-version")
    public ResponseEntity<ApiResponseDto<Void>> getJavaVersion(){
        return ApiResponseBuilder.buildSuccessResponseWithoutPayload(HttpStatus.OK, "Loans java version");
    }

    @GetMapping("/contact-info")
    public ResponseEntity<ApiResponseDto<LoansContactInfoDto>> getContactInfo(){
        return ApiResponseBuilder.buildSuccessResponse(HttpStatus.OK, "Contact Info", loansContactInfoDto);
    }

}
