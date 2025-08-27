package com.example.accounts.controller;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private final IAccountsService iAccountsService;

    private ResponseEntity<ResponseDto> buildSuccessResponse(HttpStatus httpStatus, String status, String message){
        return ResponseEntity.status(httpStatus).body(new ResponseDto(status, message));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return buildSuccessResponse(HttpStatus.CREATED, AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                     String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.updateAccount(customerDto);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "^\\d{11}$", message="Mobile number must be 11 digits")                                                         String mobileNumber) {
        iAccountsService.deleteAccount(mobileNumber);
        return buildSuccessResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
    }
}
