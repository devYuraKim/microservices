package com.example.accounts.service.client;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans")
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<ApiResponseDto<LoansDto>> fetchLoanDetails(@RequestParam String mobileNumber);

}
