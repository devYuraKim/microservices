package com.example.accounts.service.client;

import com.example.accounts.dto.ApiResponseDto;
import com.example.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<ApiResponseDto<CardsDto>> fetchCardDetails(@RequestParam String mobileNumber);

}
