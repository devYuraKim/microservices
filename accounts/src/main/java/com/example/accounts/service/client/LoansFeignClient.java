package com.example.accounts.service.client;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans")
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<ApiResponseDto<LoansDto>> fetchLoanDetails(
            @RequestHeader("microservices-correlation-id") String correlationId,
            @RequestParam String mobileNumber);

}
